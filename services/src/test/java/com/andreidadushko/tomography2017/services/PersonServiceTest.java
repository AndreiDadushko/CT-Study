package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Person;

public class PersonServiceTest extends AbstractTest {

	@Inject
	private IPersonService personService;

	private static List<Person> testData;

	@Before
	public void initializeTestData() {

		testData = new ArrayList<Person>();
		Person person0 = new Person();
		person0.setLogin("login0");
		testData.add(person0);

		Person person1 = new Person();
		person1.setLogin("login1");
		person1.setPassword("password1");
		testData.add(person1);

		Person person2 = new Person();
		person2.setFirstName("Валерий");
		person2.setMiddleName("Наставший");
		person2.setLastName("Временев");
		person2.setBirthDate(new java.sql.Timestamp(new Date().getTime()));
		person2.setPhoneNumber("+23414124124");
		person2.setAdress("Минск");
		person2.setLogin("valera");
		person2.setPassword("password");
		testData.add(person2);
		
	}

	@Test
	public void getWithIdTest() {
		Person person = personService.insert(testData.get(2));
		Person personFromDB = personService.get(person.getId());
		Assert.assertTrue("Returned data isn't correct", person.equals(personFromDB));
	}
	
	/*@Test
	public void getWithLoginTest() {
		Person person = personService.insert(testData.get(2));
		Person personFromDB = personService.get(person.getLogin());
		Assert.assertTrue("Returned data isn't correct", person.equals(personFromDB));
	}*/

	@Test(expected = IllegalArgumentException.class)
	public void insertNullTest() {		
		personService.insert(null);
		Assert.fail("Could not insert null");	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void insertOnlyLoginTest() {		
		personService.insert(testData.get(0));
		Assert.fail("Could not insert person that have only login");
	}
	
	@Test(expected = org.springframework.dao.DuplicateKeyException.class)
	public void insertNotUniqueLoginTest() {		
		personService.insert(testData.get(1));
		personService.insert(testData.get(1));
		Assert.fail("Login must be unique");
	}
	
	@Test
	public void insertTest() {
		Person person = personService.insert(testData.get(2));
		Person personFromDB = personService.get(person.getId());
		Assert.assertTrue("Inserted data isn't correct", person.equals(personFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateNullTest() {	
		personService.update(null);
		Assert.fail("Could not update null");	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updateWithoutIdTest() {	
		personService.update(testData.get(1));
		Assert.fail("Could not update without ID");	
	}
	
	@Test
	public void updateTest() {
		Person person = personService.insert(testData.get(2));
		testData.get(1).setId(person.getId());		
		personService.update(testData.get(1));
		Person personFromDB = personService.get(person.getId());		
		Assert.assertTrue("Inserted data isn't correct", testData.get(1).equals(personFromDB));
	}
	
	@Test
	public void deleteTest() {
		Person person = personService.insert(testData.get(2));
		personService.delete(person.getId());		
		Person personFromDB = personService.get(person.getId());		
		Assert.assertNull("Could not delete data", personFromDB);
	}
	
	@Test
	public void getAllTest() {		
		List<Person> persons=personService.getAll();
		int numberBeforeInsert=persons.size();
		personService.insert(testData.get(2));
		persons=personService.getAll();
		int numberAfterInsert=persons.size();	
		Assert.assertTrue("Could not get all persons", numberBeforeInsert+1==numberAfterInsert);
	}
	
}
