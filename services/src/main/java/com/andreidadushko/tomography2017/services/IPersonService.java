package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.datamodel.Person;

public interface IPersonService {

	Person get(Integer id);
	
	Boolean validateUserPassword(String login, String password);

	@Transactional
	Person insert(Person person);

	@Transactional
	void update(Person person);	

	@Transactional
	void delete(Integer id);
	
	List<Person> getAll();
		
}
