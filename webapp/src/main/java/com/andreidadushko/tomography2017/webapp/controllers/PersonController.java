package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.dao.db.filters.SortData;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.services.IPersonService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.PersonFilterModel;
import com.andreidadushko.tomography2017.webapp.models.PersonModel;
import com.andreidadushko.tomography2017.webapp.storage.UserAuthStorage;

@RestController
@RequestMapping("/person")
public class PersonController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IPersonService personService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог") || userAuthStorage.getId().equals(id)) {
			Person person = personService.get(id);
			PersonModel convertedPerson = null;
			if (person != null) {
				convertedPerson = personEntity2PersonModel(person);
			}
			return new ResponseEntity<PersonModel>(convertedPerson, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody PersonModel personModel) {
		Person person = personModel2PersonEntity(personModel);
		try {
			personService.insert(person);
			return new ResponseEntity<IntegerModel>(new IntegerModel(person.getId()), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody PersonModel personModel) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getId().equals(personModel.getId())) {
			Person person = personModel2PersonEntity(personModel);
			try {
				personService.update(person);
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			} catch (IllegalArgumentException e) {
				LOGGER.warn(e.getMessage());
				return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор") || userAuthStorage.getId().equals(id)) {
			try {
				personService.delete(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Integer result = personService.getCount();
			return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getWithPagination(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			List<Person> allPersons = personService.getWithPagination(offset, limit);
			List<PersonModel> convertedPersons = new ArrayList<PersonModel>();
			for (Person person : allPersons) {
				convertedPersons.add(personEntity2PersonModel(person));
			}
			return new ResponseEntity<List<PersonModel>>(convertedPersons, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public ResponseEntity<?> getWithPaginationAndFilter(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit, @RequestBody PersonFilterModel personFilterModel) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			PersonFilter personFilter = personFilterModel2PersonFilter(personFilterModel);
			List<Person> allPersons = personService.getWithPagination(offset, limit, personFilter);
			List<PersonModel> convertedPersons = new ArrayList<>();
			for (Person person : allPersons) {
				convertedPersons.add(personEntity2PersonModel(person));
			}
			return new ResponseEntity<List<PersonModel>>(convertedPersons, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	private PersonModel personEntity2PersonModel(Person person) {
		PersonModel personModel = new PersonModel();
		personModel.setId(person.getId());
		personModel.setFirstName(person.getFirstName());
		personModel.setMiddleName(person.getMiddleName());
		personModel.setLastName(person.getLastName());
		personModel.setBirthDate(person.getBirthDate() == null ? null : person.getBirthDate().getTime());
		personModel.setPhoneNumber(person.getPhoneNumber());
		personModel.setAdress(person.getAdress());
		return personModel;
	}

	private Person personModel2PersonEntity(PersonModel personModel) {
		Person person = new Person();
		person.setId(personModel.getId());
		person.setFirstName(personModel.getFirstName());
		person.setMiddleName(personModel.getMiddleName());
		person.setLastName(personModel.getLastName());
		person.setBirthDate(
				personModel.getBirthDate() == null ? null : new java.sql.Timestamp(personModel.getBirthDate()));
		person.setPhoneNumber(personModel.getPhoneNumber());
		person.setAdress(personModel.getAdress());
		person.setLogin(personModel.getLogin());
		person.setPassword(personModel.getPassword());
		return person;
	}

	private PersonFilter personFilterModel2PersonFilter(PersonFilterModel personFilterModel) {
		PersonFilter personFilter = new PersonFilter();
		personFilter.setFirstName(personFilterModel.getFirstName());
		personFilter.setMiddleName(personFilterModel.getMiddleName());
		personFilter.setLastName(personFilterModel.getLastName());
		personFilter.setAdress(personFilterModel.getAdress());
		personFilter.setFrom(
				personFilterModel.getFrom() == null ? null : new java.sql.Timestamp(personFilterModel.getFrom()));
		personFilter
				.setTo(personFilterModel.getTo() == null ? null : new java.sql.Timestamp(personFilterModel.getTo()));
		if (personFilterModel.getSort() != null) {
			SortData sortData = new SortData();
			sortData.setColumn(personFilterModel.getSort().getColumn());
			sortData.setOrder(personFilterModel.getSort().getOrder());
			personFilter.setSort(sortData);
		}
		return personFilter;
	}

}
