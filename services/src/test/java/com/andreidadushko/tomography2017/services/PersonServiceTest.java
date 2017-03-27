package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Person;

public class PersonServiceTest extends AbstractTest {

	@Inject
	private IPersonService personService;

	private static List<Person> testList = new ArrayList<Person>();;

	@BeforeClass
	public static void initializeTestData() {

		Person person0 = new Person();
		person0.setLogin("login1");
		testList.add(person0);

		Person person1 = new Person();
		person1.setLogin("login");
		person1.setPassword("password");
		testList.add(person1);

		Person person2 = new Person();
		person2.setLogin("origin");
		person2.setPassword("password");
		testList.add(person2);

		Person person3 = new Person();
		person3.setFirstName("Валерий");
		person3.setMiddleName("Наставший");
		person3.setLastName("Временев");
		person3.setBirthDate(new java.sql.Timestamp(new Date().getTime()));
		person3.setPhoneNumber("+23414124124");
		person3.setAdress("Минск");
		person3.setLogin("login");
		person3.setPassword("password");
		testList.add(person3);
	}

	@Test
	public void getTest() {

		Person person = personService.insert(testList.get(3));
		Person personFromDB = personService.get(person.getId());
		try {
			Assert.assertTrue("Returned data isn't correct", person.equals(personFromDB));
		} finally {
			personService.delete(personFromDB.getId());
		}

	}

	@Test
	public void insertTest() {

		Person person = personService.insert(testList.get(0));
		Assert.assertNull("Person must have login and password", person);

		person = personService.insert(testList.get(1));
		Assert.assertNull("Login must be unique", personService.insert(testList.get(1)));
		if (person != null)
			personService.delete(person.getId());

		person = personService.insert(testList.get(2));
		Person personFromDB = personService.get(person.getId());
		try {
			Assert.assertTrue("Inserted data isn't correct", person.equals(personFromDB));
		} finally {
			personService.delete(personFromDB.getId());
		}

		person = personService.insert(testList.get(3));
		personFromDB = personService.get(person.getId());
		try {
			Assert.assertTrue("Inserted data isn't correct", person.equals(personFromDB));
		} finally {
			personService.delete(personFromDB.getId());
		}

	}
}
