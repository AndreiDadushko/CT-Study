package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.datamodel.Person;

public interface IPersonService {

	Person get(Integer id);

	@Transactional
	Person insert(Person person);

	@Transactional
	void update(Person person);	

	@Transactional
	void delete(Integer id);
	
	List<Person> getAll();
	
	Person authentication(String login, String password);
	
	boolean isLoginUnique(String login);
}
