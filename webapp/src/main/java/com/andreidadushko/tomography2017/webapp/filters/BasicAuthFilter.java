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
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.services.IPersonService;
import com.andreidadushko.tomography2017.services.IStaffService;
import com.andreidadushko.tomography2017.webapp.storage.UserAuthStorage;

public class BasicAuthFilter implements Filter {

	private IPersonService personService;
	private IStaffService staffService;

	private ApplicationContext appContext;

	@Override
	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		personService = context.getBean(IPersonService.class);
		staffService = context.getBean(IStaffService.class);
		appContext = context;

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String[] credentials = resolveCredentials(req);
		boolean isCredentialsResolved = credentials != null && credentials.length == 2;
		if (!isCredentialsResolved) {
			res.sendError(HttpStatus.UNAUTHORIZED_401);
			return;
		}
		String login = credentials[0];
		String password = credentials[1];

		Person person = personService.getByLogin(login);
		if (person.getPassword().equals(password)) {
			UserAuthStorage userDataStorage = appContext.getBean(UserAuthStorage.class);
			List<String> positions = staffService.getPositionsByLogin(login);
			userDataStorage.setId(person.getId());
			userDataStorage.setPositions(positions);
			chain.doFilter(request, response);
		} else {
			res.sendError(HttpStatus.UNAUTHORIZED_401);
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

	@Override
	public void destroy() {
	}

}
