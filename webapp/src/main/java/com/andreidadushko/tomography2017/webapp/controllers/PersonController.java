package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.services.IPersonService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.PersonFilterModel;
import com.andreidadushko.tomography2017.webapp.models.PersonModel;
import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

@RestController
@RequestMapping("/person")
public class PersonController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IPersonService personService;

	@Inject
	private ConversionService conversionService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог") || userAuthStorage.getId().equals(id)) {
			Person person = personService.get(id);
			PersonModel convertedPerson = null;
			if (person != null) {
				convertedPerson = conversionService.convert(person, PersonModel.class);
			}
			LOGGER.info("{} request person with id = {}", userAuthStorage, id);
			return new ResponseEntity<PersonModel>(convertedPerson, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody PersonModel personModel) {
		Person person = conversionService.convert(personModel, Person.class);
		try {
			personService.insert(person);
			LOGGER.info("User insert person with id = {}", person.getId());
			return new ResponseEntity<IntegerModel>(new IntegerModel(person.getId()), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			LOGGER.info("User has entered incorrect data : {}", e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody PersonModel personModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getId().equals(personModel.getId())) {
			Person person = conversionService.convert(personModel, Person.class);
			try {
				personService.update(person);
				LOGGER.info("{} update person with id = {}", userAuthStorage, person.getId());
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			} catch (IllegalArgumentException e) {
				LOGGER.info("{} has entered incorrect data : {}", userAuthStorage, e.getMessage());
				return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор") || userAuthStorage.getId().equals(id)) {
			try {
				personService.delete(id);
				LOGGER.info("{} delete person with id = {}", userAuthStorage, id);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				LOGGER.error("{} could not delete person with id = {}", userAuthStorage, id);
				return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Integer result = personService.getCount();
			LOGGER.info("{} request count of persons", userAuthStorage);
			return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getWithPagination(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			List<Person> allPersons = personService.getWithPagination(offset, limit);
			List<PersonModel> convertedPersons = new ArrayList<PersonModel>();
			for (Person person : allPersons) {
				convertedPersons.add(conversionService.convert(person, PersonModel.class));
			}
			LOGGER.info("{} request persons with offset = {}, limit = {}", userAuthStorage, offset, limit);
			return new ResponseEntity<List<PersonModel>>(convertedPersons, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.OPTIONS)
	public ResponseEntity<?> getWithPaginationAndFilter(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit, @RequestBody PersonFilterModel personFilterModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			PersonFilter personFilter = conversionService.convert(personFilterModel, PersonFilter.class);
			List<Person> allPersons = personService.getWithPagination(offset, limit, personFilter);
			List<PersonModel> convertedPersons = new ArrayList<>();
			for (Person person : allPersons) {
				convertedPersons.add(conversionService.convert(person, PersonModel.class));
			}
			LOGGER.info("{} request persons with offset = {}, limit = {}, filter = {}", userAuthStorage, offset, limit,
					personFilter);
			return new ResponseEntity<List<PersonModel>>(convertedPersons, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

}
