package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.webapp.models.PersonModel;

public class PersonEntityToModelConverter implements Converter<Person, PersonModel> {

	@Override
	public PersonModel convert(Person person) {
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

}
