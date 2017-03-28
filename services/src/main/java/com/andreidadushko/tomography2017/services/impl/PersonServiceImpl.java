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
		if (isValid(person))
			return personDao.insert(person);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public void update(Person person) {
		if (isValid(person) && person.getId() != null)
			personDao.update(person);
		else
			throw new IllegalArgumentException();
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
	public Person get(String login) {
		if (login == null)
			throw new IllegalArgumentException();
		return personDao.get(login);

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
