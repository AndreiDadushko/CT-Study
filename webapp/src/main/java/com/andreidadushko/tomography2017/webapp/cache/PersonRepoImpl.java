package com.andreidadushko.tomography2017.webapp.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.andreidadushko.tomography2017.datamodel.Person;

@Component
public class PersonRepoImpl extends AbstractRepoImpl<Person> implements IPersonRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepoImpl.class);

	@Override
	protected String getKey() {
		return "Person";
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
