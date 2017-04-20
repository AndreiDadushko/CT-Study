package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.dao.db.filters.SortData;
import com.andreidadushko.tomography2017.datamodel.Person;

public class PersonServiceTest extends AbstractTest {

	@Inject
	private IPersonService personService;

	private List<Person> testData;

	@Before
	public void initializeTestData() {
		testData = new ArrayList<Person>();
		Person person0 = new Person();
		person0.setLogin(Integer.toString(new Object().hashCode()));
		testData.add(person0);

		Person person1 = new Person();
		person1.setLogin(Integer.toString(new Object().hashCode()));
		person1.setPassword("password1");
		testData.add(person1);

		Person person2 = new Person();
		person2.setFirstName("Иван");
		person2.setMiddleName("Иванович");
		person2.setLastName("Иванов");
		person2.setBirthDate(new java.sql.Timestamp(new Date().getTime()));
		person2.setPhoneNumber("+23414124124");
		person2.setAdress("Минск");
		person2.setLogin(Integer.toString(new Object().hashCode()));
		person2.setPassword("password");
		testData.add(person2);
	}

	@Test
	public void getTest() {
		Person person = personService.insert(testData.get(2));
		Person personFromDB = personService.get(person.getId());
		Assert.assertTrue("Returned data is not correct", person.equals(personFromDB));
	}

	@Test
	public void validateCorrectLoginPasswordTest() {
		Person person = personService.insert(testData.get(2));
		Assert.assertTrue("Could not validate existing person",
				personService.validateLoginPassword(person.getLogin(), person.getPassword()));
	}

	@Test
	public void validateIncorrectLoginPasswordTest() {
		String notExistingLogin = Integer.toString(new Object().hashCode());
		String notExistingPassword = Integer.toString(new Object().hashCode());
		Assert.assertFalse("Could not validate incorrect login and password",
				personService.validateLoginPassword(notExistingLogin, notExistingPassword));
		Assert.assertFalse("Could not validate null login and password",
				personService.validateLoginPassword(null, null));
	}

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

	@Test
	public void insertTest() {
		Person person = personService.insert(testData.get(2));
		Person personFromDB = personService.get(person.getId());
		Assert.assertTrue("Inserted data is not correct", person.equals(personFromDB));
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
		Assert.assertTrue("Updates data is not correct", testData.get(1).equals(personFromDB));
	}

	@Test
	public void deleteTest() {
		Person person = personService.insert(testData.get(2));
		personService.delete(person.getId());
		Person personFromDB = personService.get(person.getId());
		Assert.assertNull("Could not delete data", personFromDB);
	}

	@Test
	public void getCountTest() {
		int numberBeforeInsert = personService.getCount();
		personService.insert(testData.get(2));
		int numberAfterInsert = personService.getCount();
		Assert.assertTrue("Returned after insert count of rows is not correct", numberBeforeInsert + 1 == numberAfterInsert);
		personService.delete(testData.get(2).getId());
		int numberAfterDelete = personService.getCount();
		Assert.assertTrue("Returned after delete count of rows is not correct", numberBeforeInsert == numberAfterDelete);
	}

	@Test
	public void getWithPaginationTest() {
		personService.insert(testData.get(1));
		personService.insert(testData.get(2));
		int number = personService.getCount();
		List<Person> listFromDB = personService.getWithPagination(0, number);
		Assert.assertTrue("Number of returned rows is not correct", listFromDB.size() == number);
		listFromDB = personService.getWithPagination(number, 0);
		Assert.assertTrue("Number of returned rows is not correct", listFromDB.size() == 0);
	}
	
	@Test
	public void getWithNullFilterTest() {
		int number = personService.getCount();
		List<Person> listFromDB = personService.getWithPagination(0, number, null);
		Assert.assertTrue("Without filter must get all rows", number == listFromDB.size());
	}

	@Test
	public void getWithEmptyFilterTest() {
		int number = personService.getCount();
		PersonFilter personFilter = new PersonFilter();
		List<Person> listFromDB = personService.getWithPagination(0, number, personFilter);
		Assert.assertTrue("With empty filter must get all rows", number == listFromDB.size());
	}
	
	@Test
	public void getWithFilterTest() {
		PersonFilter personFilter = new PersonFilter();
		personFilter.setFirstName(testData.get(2).getFirstName());
		personFilter.setMiddleName(testData.get(2).getMiddleName());
		personFilter.setLastName(testData.get(2).getLastName());
		personFilter.setAdress(testData.get(2).getAdress());
		personFilter.setFrom(new java.sql.Timestamp(testData.get(2).getBirthDate().getTime() - 1000));
		personFilter.setTo(new java.sql.Timestamp(testData.get(2).getBirthDate().getTime() + 1000));
		;
		SortData sort = new SortData();
		sort.setColumn("first_name");
		sort.setOrder("ASC");
		personFilter.setSort(sort);

		personService.insert(testData.get(2));
		int number = personService.getCount();
		List<Person> listFromDB = personService.getWithPagination(0, number, personFilter);
		Assert.assertTrue("Number of returned rows is not correct", listFromDB.size() == 1);

		Person person = listFromDB.get(0);
		Assert.assertTrue("Work of filter is not correct", testData.get(2).equals(person));
	}

	@After
	public void destroyTestData() {
		personService.delete(testData.get(1).getId());
		personService.delete(testData.get(2).getId());
	}
}
