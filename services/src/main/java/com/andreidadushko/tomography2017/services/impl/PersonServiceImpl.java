package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IPersonDao;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.services.IPersonService;

@Service
public class PersonServiceImpl implements IPersonService {

	@Inject
	private IPersonDao personDao;

	@Override
	public Person get(Integer id) {
		if (id == null)
			return null;
		return personDao.get(id);

	}

	@Override
	public Person insert(Person person) {
		if (person == null)
			return null; // There is no person to insert
		if (person.getLogin() == null || person.getPassword() == null)
			return null; // Person must have login and password
		if (!isLoginUnique(person.getLogin()))
			return null; // Login must be unique
		if (person.getBirthDate() != null)
			person.getBirthDate().setNanos(0);
		return personDao.insert(person);
	}

	@Override
	public void update(Person person) {
		if (person == null)
			return; // There is no person to update
		if (person.getLogin() == null || person.getPassword() == null)
			return; // Person must have login and password
		if (!isLoginUnique(person.getLogin()))
			return; // Login must be unique
		if (person.getBirthDate() != null)
			person.getBirthDate().setNanos(0);
		personDao.update(person);

	}

	@Override
	public void delete(Integer id) {

		personDao.delete(id);

	}

	@Override
	public List<Person> getAll() {

		return personDao.getAll();

	}

	@Override
	public Person authentication(String login, String password) {

		return personDao.authentication(login, password);

	}

	@Override
	public boolean isLoginUnique(String login) {

		return personDao.isLoginUnique(login);

	}

}
