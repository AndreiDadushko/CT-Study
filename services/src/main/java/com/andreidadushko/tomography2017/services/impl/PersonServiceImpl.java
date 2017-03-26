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

		return personDao.get(id);

	}

	@Override
	public Person insert(Person person) {

		return personDao.insert(person);

	}

	@Override
	public void update(Person person) {

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

}
