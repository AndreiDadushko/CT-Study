package com.andreidadushko.tomography2017.dao.db;

import java.util.List;

import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.datamodel.Person;

public interface IPersonDao extends IAbstractDao<Person> {

	Person get(String login);
	
	Integer getCount();

	List<Person> getWithPagination(int offset, int limit);

	List<Person> getWithPagination(int offset, int limit, PersonFilter personFilter);
}
