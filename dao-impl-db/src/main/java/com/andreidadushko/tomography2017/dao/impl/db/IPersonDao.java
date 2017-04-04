package com.andreidadushko.tomography2017.dao.impl.db;

import com.andreidadushko.tomography2017.datamodel.Person;

public interface IPersonDao extends IAbstractDao<Person> {

	Person get(String login);
		
}
