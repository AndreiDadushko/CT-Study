package com.andreidadushko.tomography2017.webapp.cache;

import org.springframework.stereotype.Component;

import com.andreidadushko.tomography2017.datamodel.Person;

@Component
public class PersonRepoImpl extends AbstractRepoImpl<Person> implements IPersonRepo {

	@Override
	protected String getKey() {
		return "Person";
	}

}
