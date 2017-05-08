package com.andreidadushko.tomography2017.webapp.filters;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

public class LanguageFilter implements Filter {

	private ApplicationContext appContext;

	@Override
	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		appContext = context;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		CurrentUserData userDataStorage = appContext.getBean(CurrentUserData.class);
		Locale locale = req.getLocale();
		userDataStorage.setLocale(locale);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
