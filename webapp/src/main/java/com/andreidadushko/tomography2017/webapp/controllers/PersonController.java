package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
import com.andreidadushko.tomography2017.webapp.models.BolleanModel;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.PersonFilterModel;
import com.andreidadushko.tomography2017.webapp.models.PersonModel;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Inject
	private IPersonService personService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Person person = personService.get(id);
		PersonModel convertedPerson = null;
		if (person != null) {
			convertedPerson = personEntity2PersonModel(person);
		}
		return new ResponseEntity<PersonModel>(convertedPerson, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody PersonModel personModel) {
		Person person = personModel2PersonEntity(personModel);
		try {
			personService.insert(person);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<IntegerModel>(new IntegerModel(person.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/authentication", method = RequestMethod.GET)
	public ResponseEntity<?> validateLoginPassword(@RequestParam(required = true) String login,
			@RequestParam(required = true) String password) {
		Boolean result = personService.validateLoginPassword(login, password);
		return new ResponseEntity<BolleanModel>(new BolleanModel(result), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody PersonModel personModel) {
		Person person = personModel2PersonEntity(personModel);
		try {
			personService.update(person);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		personService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		Integer result = personService.getCount();
		return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getWithPagination(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit) {
		List<Person> allPersons = personService.getWithPagination(offset, limit);
		List<PersonModel> convertedPersons = new ArrayList<PersonModel>();
		for (Person person : allPersons) {
			convertedPersons.add(personEntity2PersonModel(person));
		}
		return new ResponseEntity<List<PersonModel>>(convertedPersons, HttpStatus.OK);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public ResponseEntity<?> getWithPaginationAndFilter(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit, @RequestBody PersonFilterModel personFilterModel) {
		PersonFilter personFilter = personFilterModel2PersonFilter(personFilterModel);
		List<Person> allPersons = personService.getWithPagination(offset, limit, personFilter);
		List<PersonModel> convertedPersons = new ArrayList<>();
		for (Person person : allPersons) {
			convertedPersons.add(personEntity2PersonModel(person));
		}
		return new ResponseEntity<List<PersonModel>>(convertedPersons, HttpStatus.OK);
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
