package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

public class StudyProtocolServiceTest extends AbstractTest {

	@Inject
	private IStudyProtocolService studyProtocolService;

	@Inject
	private IStaffService staffService;

	@Inject
	private IPersonService personService;

	@Inject
	private IStudyService studyService;

	private List<StudyProtocol> testData;
	
	private Person person;
	private Staff staff;
	private Study study;
	private Study study1;

	@Before
	public void initializeTestData() {
		person = new Person();
		person.setLogin(Integer.toString(new Object().hashCode()));
		person.setPassword("password");
		person = personService.insert(person);

		staff = new Staff();
		staff.setDepartment("РКД");
		staff.setPosition("Врач-рентгенолог");
		staff.setPersonId(person.getId());
		staff = staffService.insert(staff);

		study = new Study();
		study.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study.setPermitted(true);
		study.setPersonId(person.getId());
		study.setStaffId(staff.getId());
		studyService.insert(study);

		study1 = new Study();
		study1.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study1.setPermitted(true);
		study1.setPersonId(person.getId());
		study1.setStaffId(staff.getId());
		studyService.insert(study1);

		testData = new ArrayList<StudyProtocol>();

		StudyProtocol studyProtocol0 = new StudyProtocol();
		testData.add(studyProtocol0);

		StudyProtocol studyProtocol1 = new StudyProtocol();
		studyProtocol1.setId(study.getId());
		studyProtocol1.setProtocol("Протокоооол");
		testData.add(studyProtocol1);

		StudyProtocol studyProtocol2 = new StudyProtocol();
		studyProtocol2.setId(study1.getId());
		studyProtocol2.setProtocol("Протокоооол 2");
		testData.add(studyProtocol2);
	}

	@Test
	public void getTest() {
		StudyProtocol studyProtocol = studyProtocolService.insert(testData.get(1));
		StudyProtocol studyProtocolFromDB = studyProtocolService.get(studyProtocol.getId());
		studyProtocol.setCreationDate(studyProtocolFromDB.getCreationDate());
		Assert.assertTrue("Returned data is not correct", studyProtocol.equals(studyProtocolFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertNullTest() {
		studyProtocolService.insert(null);
		Assert.fail("Could not insert null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertEmptyStudyProtocolTest() {
		studyProtocolService.insert(testData.get(0));
		Assert.fail("Could not insert empty study protocol");
	}

	@Test
	public void insertTest() {
		StudyProtocol studyProtocol = studyProtocolService.insert(testData.get(1));
		StudyProtocol studyProtocolFromDB = studyProtocolService.get(studyProtocol.getId());
		studyProtocol.setCreationDate(studyProtocolFromDB.getCreationDate());
		Assert.assertTrue("Inserted data is not correct", studyProtocol.equals(studyProtocolFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateNullTest() {
		studyProtocolService.update(null);
		Assert.fail("Could not update null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateEmptyStudyProtocolTest() {
		studyProtocolService.update(testData.get(0));
		Assert.fail("Could not update empty study protocol");
	}

	@Test
	public void updateTest() {
		studyProtocolService.insert(testData.get(1));
		testData.get(1).setProtocol("Новый протокол");
		studyProtocolService.update(testData.get(1));
		StudyProtocol studyProtocolFromDB = studyProtocolService.get(testData.get(1).getId());
		testData.get(1).setCreationDate(studyProtocolFromDB.getCreationDate());
		Assert.assertTrue("Updated data is not correct", testData.get(1).equals(studyProtocolFromDB));
	}

	@Test
	public void deleteTest() {
		studyProtocolService.insert(testData.get(1));
		studyProtocolService.delete(testData.get(1).getId());
		StudyProtocol studyProtocolFromDB = studyProtocolService.get(testData.get(1).getId());
		Assert.assertNull("Could not delete data", studyProtocolFromDB);
	}

	@Test
	public void getCountTest() {
		int numberBeforeInsert = studyProtocolService.getCount();
		studyProtocolService.insert(testData.get(1));
		int numberAfterInsert = studyProtocolService.getCount();
		Assert.assertTrue("Returned after insert count of rows is not correct",
				numberBeforeInsert + 1 == numberAfterInsert);
		studyProtocolService.delete(testData.get(1).getId());
		int numberAfterDelete = studyProtocolService.getCount();
		Assert.assertTrue("Returned after delete count of rows is not correct",
				numberBeforeInsert == numberAfterDelete);
	}

	@Test
	public void massDeleteTest() {
		studyProtocolService.insert(testData.get(1));
		studyProtocolService.insert(testData.get(2));
		studyProtocolService.massDelete(new Integer[] { testData.get(1).getId(), testData.get(2).getId() });

		StudyProtocol studyProtocolFromDB1 = studyProtocolService.get(testData.get(1).getId());
		StudyProtocol studyProtocolFromDB2 = studyProtocolService.get(testData.get(2).getId());
		Assert.assertTrue("Could not delete study protocol",
				studyProtocolFromDB1 == null && studyProtocolFromDB2 == null);
	}
	
	@After
	public void destroyTestData() {
		studyProtocolService.delete(testData.get(1).getId());
		studyProtocolService.delete(testData.get(2).getId());
		studyService.delete(study.getId());
		studyService.delete(study1.getId());
		staffService.delete(staff.getId());
		personService.delete(person.getId());
	}
}
