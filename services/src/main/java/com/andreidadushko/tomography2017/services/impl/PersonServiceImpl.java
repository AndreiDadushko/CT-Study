package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IPersonDao;
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
	public Person get(String login) {
		if (login == null)
			return null;
		LOGGER.info("Get person with login = " + login);
		return personDao.get(login);
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
	public List<Person> getAll() {
		List<Person> persons = personDao.getAll();
		LOGGER.info("Get list of all persons");
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
