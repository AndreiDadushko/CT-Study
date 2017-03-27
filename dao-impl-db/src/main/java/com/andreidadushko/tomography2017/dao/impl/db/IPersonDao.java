package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.datamodel.Person;

public interface IPersonDao {
	
	Person get(Integer id);

	Person insert(Person person);

	void update(Person person);	

	void delete(Integer id);
	
	List<Person> getAll();
	
	Person authentication(String login, String password);
	
	boolean isLoginUnique(String login);
}
