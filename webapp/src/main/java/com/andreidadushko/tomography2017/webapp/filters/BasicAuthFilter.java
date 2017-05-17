package com.andreidadushko.tomography2017.webapp.filters;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.services.IPersonService;
import com.andreidadushko.tomography2017.services.IStaffService;
import com.andreidadushko.tomography2017.webapp.cache.IPersonRepo;
import com.andreidadushko.tomography2017.webapp.cache.IPositionsRepo;
import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

public class BasicAuthFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthFilter.class);

	private IPersonService personService;
	private IStaffService staffService;

	private ApplicationContext appContext;

	private IPersonRepo personRepo;
	private IPositionsRepo positionsRepo;

	@Override
	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		personService = context.getBean(IPersonService.class);
		staffService = context.getBean(IStaffService.class);
		personRepo = context.getBean(IPersonRepo.class);
		positionsRepo = context.getBean(IPositionsRepo.class);
		appContext = context;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (isAuth(req, res))
			return;
		if (req.getRequestURI().matches("^/person.*") && req.getMethod().toUpperCase().equals("POST")) {
			chain.doFilter(request, response);
		} else {
			String[] credentials = resolveCredentials(req);
			boolean isCredentialsResolved = credentials != null && credentials.length == 2;
			if (!isCredentialsResolved) {
				res.sendError(HttpStatus.UNAUTHORIZED_401);
				return;
			}
			String login = credentials[0];
			String password = credentials[1];

			Person person = findPerson(login);

			if (person != null && person.getPassword().equals(password)) {
				CurrentUserData userDataStorage = appContext.getBean(CurrentUserData.class);
				List<String> positions = findPositions(login);
				if (req.getRequestURI().matches("^/staff.*") && !positions.contains("Администратор")) {
					res.sendError(HttpStatus.METHOD_NOT_ALLOWED_405);
				} else {
					userDataStorage.setId(person.getId());
					userDataStorage.setPositions(positions);
					chain.doFilter(request, response);
				}
			} else {
				res.sendError(HttpStatus.UNAUTHORIZED_401);
			}
		}
	}

	private String[] resolveCredentials(HttpServletRequest req) {
		try {
			Enumeration<String> headers = req.getHeaders("Authorization");
			String nextElement = headers.nextElement();
			String base64Credentials = nextElement.substring("Basic".length()).trim();
			String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
			return credentials.split(":", 2);
		} catch (Exception e) {
			return null;
		}
	}

	private String makeCredentials(String login, String password) {
		String logPas = login + ":" + password;
		String encodedLogPas = new String(Base64.getEncoder().encode(logPas.getBytes()), Charset.forName("UTF-8"));
		String header = "Basic " + encodedLogPas;
		return header;
	}

	private Person findPerson(String login) {
		Person person = personRepo.find(login);
		// personRepo.cleanCache();
		if (person != null) {
			LOGGER.debug("Get data from CACHE. Login: {}", login);
		}
		if (person == null) {
			person = personService.getByLogin(login);
			LOGGER.debug("Get data from DATA BASE. Login: {}", login);
			if (person != null)
				personRepo.save(login, person);
		}
		return person;
	}

	private List<String> findPositions(String login) {
		List<String> positions = positionsRepo.find(login);
		// positionsRepo.cleanCache();
		if (positions != null) {
			LOGGER.debug("Get data from CACHE. Positions: {}", positions);
		}
		if (positions == null) {
			positions = staffService.getPositionsByLogin(login);
			LOGGER.debug("Get data from DATA BASE. Positions: {}", positions);
			if (positions != null) {
				positionsRepo.save(login, positions);
			}
		}
		return positions;
	}

	private Boolean isAuth(HttpServletRequest req, HttpServletResponse res) throws IOException {
		if (req.getRequestURI().matches("^/auth.*") && req.getMethod().toUpperCase().equals("GET")
				&& req.getParameterMap().containsKey("login") && req.getParameterMap().containsKey("password")) {
			String login = req.getParameter("login");
			String password = req.getParameter("password");
			Person person = findPerson(login);
			if (person != null && person.getPassword().equals(password)) {
				res.setHeader("Authorization", makeCredentials(login, password));
			} else
				res.sendError(HttpStatus.UNAUTHORIZED_401);
			return true;
		}
		return false;
	}

	@Override
	public void destroy() {
	}

}
