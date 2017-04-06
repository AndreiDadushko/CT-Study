package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.datamodel.Study;

public class StudyServiceTest extends AbstractTest{

	@Inject
	private IStudyService studyService;
	
	@Inject
	private IStaffService staffService;

	@Inject
	private IPersonService personService;
	
	private static List<Study> testData;
	
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
		person1.setLogin("udal");
		person1.setPassword("password");
		person1 = personService.insert(person1);
		
		Staff staff = new Staff();
		staff.setDepartment("РКД");
		staff.setPosition("врач-рентгенолог");
		staff.setStartDate(new java.sql.Timestamp(new Date().getTime()));
		staff.setPersonId(person.getId());
		staff = staffService.insert(staff);
		
		Staff staff1 = new Staff();
		staff1.setDepartment("РКД");
		staff1.setPosition("санитар");		
		staff1.setPersonId(person1.getId());
		staff1 = staffService.insert(staff1);
		
		testData = new ArrayList<Study>();
		
		Study study0=new Study();
		testData.add(study0);
		
		Study study1=new Study();
		study1.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study1.setPermitted(true);
		study1.setPersonId(person.getId());
		study1.setStaffId(staff.getId());
		testData.add(study1);	
		
		Study study2=new Study();
		study2.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study2.setPermitted(true);
		study2.setPersonId(person1.getId());
		study2.setStaffId(staff1.getId());
		testData.add(study2);
	}
	
	@Test
	public void insertGetTest() {
		Study study = studyService.insert(testData.get(1));
		Study studyFromDB = studyService.get(study.getId());
		Assert.assertTrue("Returned data isn't correct", study.equals(studyFromDB));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void insertNullTest() {	
		studyService.insert(null);
		Assert.fail("Could not insert null");	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void insertEmptyStudyTest() {	
		studyService.insert(testData.get(0));
		Assert.fail("Could not insert empty study");	
	}
	
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void insertWrongPersonStaffIdTest() {
		staffService.delete(testData.get(1).getStaffId());
		personService.delete(testData.get(1).getPersonId());
		studyService.insert(testData.get(1));
		Assert.fail("Could not insert study without existing person and staff");	
	}	
	
	@Test (expected = IllegalArgumentException.class)
	public void updateNullTest() {	
		studyService.update(null);
		Assert.fail("Could not update null");	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateEmptyStudyTest() {	
		Study study = studyService.insert(testData.get(1));
		testData.get(0).setId(study.getId());
		studyService.update(testData.get(0));
		Assert.fail("Could not insert empty study");	
	}
	
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void updateWrongPersonStaffIdTest() {
		Study study = studyService.insert(testData.get(1));
		staffService.delete(testData.get(2).getStaffId());
		personService.delete(testData.get(2).getPersonId());
		testData.get(2).setId(study.getId());
		studyService.update(testData.get(2));
		Assert.fail("Could not insert study without existing person and staff");	
	}
	
	@Test 
	public void updateTest() {
		Study study = studyService.insert(testData.get(1));
		testData.get(2).setId(study.getId());
		studyService.update(testData.get(2));
		Study studyFromDB = studyService.get(study.getId());
		Assert.assertTrue("Updated data isn't correct", testData.get(2).equals(studyFromDB));
	}
	
	@Test 
	public void deleteTest() {
		Study study = studyService.insert(testData.get(1));
		studyService.delete(study.getId());
		Study studyFromDB = studyService.get(study.getId());
		Assert.assertNull("Could not delete data", studyFromDB);
	}
	
	/*@Test
	public void getAllTest() {		
		List<Study> studies=studyService.getAll();
		int numberBeforeInsert=studies.size();
		studyService.insert(testData.get(1));
		studies=studyService.getAll();
		int numberAfterInsert=studies.size();	
		Assert.assertTrue("Could not get all studies", numberBeforeInsert+1==numberAfterInsert);
	}*/
	
}
