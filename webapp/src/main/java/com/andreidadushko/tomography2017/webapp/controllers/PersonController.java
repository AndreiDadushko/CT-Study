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

import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.services.IPersonService;
import com.andreidadushko.tomography2017.webapp.models.IdModel;
import com.andreidadushko.tomography2017.webapp.models.PersonModel;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Inject
	private IPersonService personService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit) {
		List<Person> allPersons = personService.getWithPagination(offset, limit);
		List<PersonModel> convertedPersons = new ArrayList<>();
		for (Person person : allPersons) {
			convertedPersons.add(entity2model(person));
		}
		return new ResponseEntity<List<PersonModel>>(convertedPersons, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Person person = personService.get(id);
		PersonModel convertedPerson=null;
		if (person != null) {
			convertedPerson = entity2model(person);
		}
		return new ResponseEntity<PersonModel>(convertedPerson, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createBook(@RequestBody PersonModel personModel) {
		Person person = model2entity(personModel);
		personService.insert(person);
        return new ResponseEntity<IdModel>(new IdModel(person.getId()), HttpStatus.CREATED);
    }

	private PersonModel entity2model(Person person) {
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
	
	private Person model2entity(PersonModel personModel) {
		Person person = new Person();
		person.setId(personModel.getId());
		person.setFirstName(personModel.getFirstName());
		person.setMiddleName(personModel.getMiddleName());
		person.setLastName(personModel.getLastName());
		person.setBirthDate(personModel.getBirthDate() == null ? null : new java.sql.Timestamp(personModel.getBirthDate()));
		person.setPhoneNumber(personModel.getPhoneNumber());
		person.setAdress(personModel.getAdress());
		person.setLogin(personModel.getLogin());
		person.setPassword(personModel.getPassword());
		return person;
	}
}
