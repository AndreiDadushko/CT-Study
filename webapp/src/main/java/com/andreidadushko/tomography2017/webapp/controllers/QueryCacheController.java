package com.andreidadushko.tomography2017.webapp.controllers;

import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.dao.db.cache.QueryCache;
import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

@RestController
@RequestMapping("/cache")
public class QueryCacheController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryCacheController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private QueryCache queryCache;

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ResponseEntity<?> save() {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")) {
			try {
				queryCache.saveCache();
				LOGGER.info("{} has saved cache", userAuthStorage);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (IOException e) {
				LOGGER.error("{} could not save cache. Exception: {}", userAuthStorage, e.toString());
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public ResponseEntity<?> load() {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")) {
			try {
				queryCache.loadCache();
				LOGGER.info("{} has loaded cache", userAuthStorage);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				LOGGER.error("{} could not load cache. Exception: {}", userAuthStorage, e.toString());
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

}
