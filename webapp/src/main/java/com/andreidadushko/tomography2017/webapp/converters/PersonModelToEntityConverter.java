package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.webapp.models.PersonModel;

public class PersonModelToEntityConverter implements Converter<PersonModel, Person> {

	@Override
	public Person convert(PersonModel personModel) {
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

}
