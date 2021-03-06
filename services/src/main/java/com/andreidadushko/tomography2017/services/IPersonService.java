package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.datamodel.Person;

public interface IPersonService {

	Person get(Integer id);

	Person getByLogin(String login);

	@Transactional
	Person insert(Person person);

	@Transactional
	void update(Person person);

	@Transactional
	void delete(Integer id);
	
	Integer getCount();

	List<Person> getWithPagination(int offset, int limit);
	
	List<Person> getWithPagination(int offset, int limit, PersonFilter personFilter);

}
