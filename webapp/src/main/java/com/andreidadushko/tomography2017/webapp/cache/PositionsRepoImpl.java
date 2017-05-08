package com.andreidadushko.tomography2017.webapp.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PositionsRepoImpl extends AbstractRepoImpl<List<String>> implements IPositionsRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionsRepoImpl.class);

	@Override
	protected String getKey() {
		return "Positions";
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
