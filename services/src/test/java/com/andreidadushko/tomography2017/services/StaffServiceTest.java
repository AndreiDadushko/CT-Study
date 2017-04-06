package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.datamodel.Staff;

import org.junit.Assert;

public class StaffServiceTest extends AbstractTest {

	@Inject
	private IStaffService staffService;

	@Inject
	private IPersonService personService;

	private static List<Staff> testData;

	@Before
	public void initializeTestData() {
		Person person = new Person();
		person.setFirstName("Валерий");
		person.setMiddleName("Наставший");
		person.setLastName("Временев");
		person.setBirthDate(new java.sql.Timestamp(new Date().getTime()));
		person.setPhoneNumber("+23414124124");
		person.setAdress("Минск");
		person.setLogin("valera");
		person.setPassword("password");
		person = personService.insert(person);

		Person person1 = new Person();		
		person1.setLogin("dima");
		person1.setPassword("dimapass");
		person1 = personService.insert(person1);
		
		testData = new ArrayList<Staff>();
		Staff staff0 = new Staff();
		testData.add(staff0);

		Staff staff1 = new Staff();
		staff1.setDepartment("РКД");
		staff1.setPosition("Лаборант");	
		staff1.setPersonId(person1.getId());
		testData.add(staff1);

		Staff staff2 = new Staff();
		staff2.setDepartment("РКД");
		staff2.setPosition("врач-рентгенолог");
		staff2.setStartDate(new java.sql.Timestamp(new Date().getTime()));
		staff2.setPersonId(person.getId());
		testData.add(staff2);
	}

	@Test
	public void insertGetTest() {
		Staff staff = staffService.insert(testData.get(2));
		Staff staffFromDB = staffService.get(staff.getId());
		Assert.assertTrue("Returned data isn't correct", staff.equals(staffFromDB));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void insertNullTest() {		
		staffService.insert(null);
		Assert.fail("Could not insert null");	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void insertEmptyStaffTest() {		
		staffService.insert(testData.get(0));
		Assert.fail("Could not insert empty staff");	
	}
		
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void insertWrongIdTest() {
		personService.delete(testData.get(2).getPersonId());
		staffService.insert(testData.get(2));
		Assert.fail("Could not insert staff without existing person");	
	}
		
	@Test(expected = IllegalArgumentException.class)
	public void updateNullTest() {
		staffService.update(null);
		Assert.fail("Could not update null");	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updateEmptyStaffTest() {
		staffService.update(testData.get(0));
		Assert.fail("Could not update empty staff");	
	}
	
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void updateWrongIdTest() {
		Staff staff = staffService.insert(testData.get(2));
		personService.delete(testData.get(1).getPersonId());
		testData.get(1).setId(staff.getId());
		staffService.update(testData.get(1));
		Assert.fail("Could not update staff without existing person");	
	}
	
	@Test
	public void updateTest() {		
		Staff staff = staffService.insert(testData.get(2));		
		staffService.update(testData.get(2));
		Staff staffFromDB = staffService.get(staff.getId());
		Assert.assertTrue("Updated data isn't correct", testData.get(2).equals(staffFromDB));
	}
	
	@Test
	public void deleteTest() {
		Staff staff = staffService.insert(testData.get(2));	
		staffService.delete(staff.getId());
		Staff staffFromDB = staffService.get(staff.getId());
		Assert.assertNull("Could not delete data", staffFromDB);
	}
	
	/*@Test
	public void getAllTest() {		
		List<Staff> staff=staffService.getAll();
		int numberBeforeInsert=staff.size();
		staffService.insert(testData.get(2));
		staff=staffService.getAll();
		int numberAfterInsert=staff.size();	
		Assert.assertTrue("Could not get all staff", numberBeforeInsert+1==numberAfterInsert);
	}*/

}
