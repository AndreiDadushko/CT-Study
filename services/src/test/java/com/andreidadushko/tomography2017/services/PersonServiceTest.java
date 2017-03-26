package com.andreidadushko.tomography2017.services;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Person;

public class PersonServiceTest extends AbstractTest {

	@Inject
	private IPersonService personService;

	@Test
	public void getTest() {
		Person person = new Person();
		person.setFirstName("Валерий");
		person.setMiddleName("Наставший");
		person.setLastName("Временев");		
		person.setBirthDate(new java.sql.Timestamp(new Date().getTime()));
		person.setPhoneNumber("+23414124124");
		person.setAdress("Минск");
		person.setLogin("login");
		person.setPassword("password");

		person = personService.insert(person);

		Person personFromDB = personService.get(person.getId());
		System.out.println(person);
		System.out.println(personFromDB);

		try {
			Assert.assertTrue("Returned data isn't correct", person.equals(personFromDB));
		} finally {
			personService.delete(personFromDB.getId());
		}
	}

}
