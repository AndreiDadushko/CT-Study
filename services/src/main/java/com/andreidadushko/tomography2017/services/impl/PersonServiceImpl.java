package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.db.IPersonDao;
import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.services.IPersonService;

@Service
public class PersonServiceImpl implements IPersonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Inject
	private IPersonDao personDao;

	@Override
	public Person get(Integer id) {
		LOGGER.info("Get person with id = " + id);
		if (id == null)
			return null;
		return personDao.get(id);
	}

	@Override
	public Person getByLogin(String login) {
		if (login == null)
			return null;
		Person person = personDao.get(login);
		LOGGER.info("Get person with login = " + login);
		return person;
	}

	@Override
	public Person insert(Person person) {
		isValid(person);
		personDao.insert(person);
		LOGGER.info("Insert person with id = " + person.getId());
		return person;
	}

	@Override
	public void update(Person person) {
		if (isValid(person) && person.getId() != null) {
			personDao.update(person);
			LOGGER.info("Update person with id = " + person.getId());
		} else
			throw new IllegalArgumentException("Could not update person without id");
	}

	@Override
	public void delete(Integer id) {
		personDao.delete(id);
		LOGGER.info("Delete person with id = " + id);
	}

	@Override
	public Integer getCount() {

		return personDao.getCount();
	}

	@Override
	public List<Person> getWithPagination(int offset, int limit) {
		List<Person> persons = personDao.getWithPagination(offset, limit);
		LOGGER.info("Get list of persons with offset = {}, limit = {}", offset, limit);
		return persons;
	}

	@Override
	public List<Person> getWithPagination(int offset, int limit, PersonFilter personFilter) {
		List<Person> persons = personDao.getWithPagination(offset, limit, personFilter);
		LOGGER.info("Get list of persons with offset = {}, limit = {} and filter = {}", offset, limit, personFilter);
		return persons;
	}

	private boolean isValid(Person person) {
		if (person == null)
			throw new IllegalArgumentException("Could not insert/update null");
		if (person.getLogin() == null || person.getPassword() == null)
			throw new IllegalArgumentException("Person must have login and password");
		return true;
	}

}
