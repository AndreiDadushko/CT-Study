package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IPersonDao;
import com.andreidadushko.tomography2017.dao.impl.db.filters.PersonFilter;
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
	public Boolean validateUserPassword(String login, String password) {
		if (login == null || password == null)
			return false;
		Person person = personDao.get(login);
		if (password.equals(person.getPassword()))
			return true;
		else
			return false;
	}

	@Override
	public Person insert(Person person) {
		if (isValid(person)) {
			personDao.insert(person);
			LOGGER.info("Insert person with id = " + person.getId());
			return person;
		} else
			throw new IllegalArgumentException();
	}

	@Override
	public void update(Person person) {
		if (isValid(person) && person.getId() != null) {
			personDao.update(person);
			LOGGER.info("Update person with id = " + person.getId());
		} else
			throw new IllegalArgumentException();
	}

	@Override
	public void delete(Integer id) {
		personDao.delete(id);
		LOGGER.info("Delete person with id = " + id);
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
		LOGGER.info("Get list of persons with offset = {}, limit = {} and filter = ", offset, limit, personFilter);
		return persons;
	}

	private boolean isValid(Person person) {
		if (person == null)
			return false;
		if (person.getLogin() == null || person.getPassword() == null)
			return false;
		if (person.getBirthDate() != null)
			person.getBirthDate().setNanos(0);
		return true;
	}

}
