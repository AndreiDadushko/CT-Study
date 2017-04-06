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
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

public class StudyProtocolServiceTest extends AbstractTest{
	
	@Inject
	private IStudyProtocolService studyProtocolService;
	
	@Inject
	private IStaffService staffService;

	@Inject
	private IPersonService personService;
	
	@Inject
	private IStudyService studyService;
	
	private static List<StudyProtocol> testData;
	
	@Before
	public void initializeTestData() {
		
		Person person = new Person();		
		person.setLogin("valera");
		person.setPassword("password");
		person = personService.insert(person);
		
		Staff staff = new Staff();
		staff.setDepartment("РКД");
		staff.setPosition("санитар");		
		staff.setPersonId(person.getId());
		staff = staffService.insert(staff);
		
		Study study=new Study();
		study.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study.setPermitted(true);
		study.setPersonId(person.getId());
		study.setStaffId(staff.getId());
		studyService.insert(study);
		
		testData = new ArrayList<StudyProtocol>();
		
		StudyProtocol studyProtocol0 = new StudyProtocol();		
		testData.add(studyProtocol0);
		
		StudyProtocol studyProtocol1 = new StudyProtocol();
		studyProtocol1.setId(study.getId());
		studyProtocol1.setProtocol("Протокоооол");
		testData.add(studyProtocol1);
		
	}
	
	@Test
	public void insertGetTest() {
		StudyProtocol studyProtocol=studyProtocolService.insert(testData.get(1));
		StudyProtocol studyProtocolFromDB=studyProtocolService.get(studyProtocol.getId());
		studyProtocol.setCreationDate(studyProtocolFromDB.getCreationDate());
		Assert.assertTrue("Returned data isn't correct", studyProtocol.equals(studyProtocolFromDB));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void insertNullTest() {	
		studyProtocolService.insert(null);
		Assert.fail("Could not insert null");	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void insertEmptyStudyProtocolTest() {
		studyProtocolService.insert(testData.get(0));
		Assert.fail("Could not insert empty study protocol");	
	}
	
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void insertWrongIdTest() {
		studyService.delete(testData.get(1).getId());
		studyProtocolService.insert(testData.get(1));
		Assert.fail("Could not insert study protocol without existing study");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateNullTest() {	
		studyProtocolService.update(null);
		Assert.fail("Could not update null");	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateEmptyStudyProtocolTest() {
		studyProtocolService.update(testData.get(0));
		Assert.fail("Could not update empty study protocol");	
	}
	
	@Test 
	public void updateTest() {
		studyProtocolService.insert(testData.get(1));
		testData.get(1).setProtocol("Новый протокол");
		studyProtocolService.update(testData.get(1));
		StudyProtocol studyProtocolFromDB=studyProtocolService.get(testData.get(1).getId());
		testData.get(1).setCreationDate(studyProtocolFromDB.getCreationDate());
		Assert.assertTrue("Updated data isn't correct", testData.get(1).equals(studyProtocolFromDB));
	}
	
	@Test 
	public void deleteTest() {
		studyProtocolService.insert(testData.get(1));
		studyProtocolService.delete(testData.get(1).getId());
		StudyProtocol studyProtocolFromDB=studyProtocolService.get(testData.get(1).getId());
		Assert.assertNull("Could not delete data", studyProtocolFromDB);
	}
	/*
	@Test
	public void getAllTest() {		
		List<StudyProtocol> studiesProtocols=studyProtocolService.getAll();
		int numberBeforeInsert=studiesProtocols.size();
		studyProtocolService.insert(testData.get(1));
		studiesProtocols=studyProtocolService.getAll();
		int numberAfterInsert=studiesProtocols.size();	
		Assert.assertTrue("Could not get all studies protocols", numberBeforeInsert+1==numberAfterInsert);
	}	
	*/
}
