package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.impl.db.filters.SortData;
import com.andreidadushko.tomography2017.dao.impl.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

public class StudyServiceTest extends AbstractTest {

	@Inject
	private IStudyService studyService;

	@Inject
	private IStaffService staffService;

	@Inject
	private IPersonService personService;

	@Inject
	private IStudyOfferCartService studyOfferCartService;

	@Inject
	private IOfferService offerService;

	@Inject
	private ICategoryService categoryService;

	@Inject
	private IStudyProtocolService studyProtocolService;

	private static List<Study> testData;

	private static StudyForList testStudyForList;

	Person person;

	@Before
	public void initializeTestData() {
		person = new Person();
		person.setFirstName("Иван");
		person.setMiddleName("Иванович");
		person.setLastName("Иванов");
		person.setBirthDate(new java.sql.Timestamp(new Date().getTime()));
		person.setPhoneNumber("+23414124124");
		person.setAdress("Минск");
		person.setLogin(Integer.toString(new Object().hashCode()));
		person.setPassword("password");
		person = personService.insert(person);

		Person person1 = new Person();
		person1.setLogin(Integer.toString(new Object().hashCode()));
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

		Study study0 = new Study();
		testData.add(study0);

		Study study1 = new Study();
		study1.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study1.setPermitted(true);
		study1.setPersonId(person.getId());
		study1.setStaffId(staff.getId());
		testData.add(study1);

		Study study2 = new Study();
		study2.setAppointmentDate(new java.sql.Timestamp(new Date().getTime() + 10000));
		study2.setPermitted(true);
		study2.setPersonId(person1.getId());
		study2.setStaffId(staff1.getId());
		testData.add(study2);

		testStudyForList = new StudyForList();
		testStudyForList.setAppointmentDate(study1.getAppointmentDate());
		testStudyForList.setPermitted(study1.getPermitted());
		testStudyForList.setPatientFirstName(person.getFirstName());
		testStudyForList.setPatientMiddleName(person.getMiddleName());
		testStudyForList.setPatientLastName(person.getLastName());
		testStudyForList.setDoctorFirstName(person.getFirstName());
		testStudyForList.setDoctorMiddleName(person.getMiddleName());
		testStudyForList.setDoctorLastName(person.getLastName());
	}

	@Test
	public void getTest() {
		Study study = studyService.insert(testData.get(1));
		Study studyFromDB = studyService.get(study.getId());
		Assert.assertTrue("Returned data is not correct", study.equals(studyFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertNullTest() {
		studyService.insert(null);
		Assert.fail("Could not insert null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertEmptyStudyTest() {
		studyService.insert(testData.get(0));
		Assert.fail("Could not insert empty study");
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void insertWrongPersonStaffIdTest() {
		staffService.delete(testData.get(1).getStaffId());
		personService.delete(testData.get(1).getPersonId());
		studyService.insert(testData.get(1));
		Assert.fail("Could not insert study without existing person and staff");
	}

	@Test
	public void insertTest() {
		Study study = studyService.insert(testData.get(1));
		Study studyFromDB = studyService.get(study.getId());
		Assert.assertTrue("Inserted data is not correct", study.equals(studyFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateNullTest() {
		studyService.update(null);
		Assert.fail("Could not update null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateEmptyStudyTest() {
		Study study = studyService.insert(testData.get(1));
		testData.get(0).setId(study.getId());
		studyService.update(testData.get(0));
		Assert.fail("Could not insert empty study");
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
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
		Assert.assertTrue("Updated data is not correct", testData.get(2).equals(studyFromDB));
	}

	@Test
	public void deleteTest() {
		Study study = studyService.insert(testData.get(1));
		studyService.delete(study.getId());
		Study studyFromDB = studyService.get(study.getId());
		Assert.assertNull("Could not delete data", studyFromDB);
	}

	@Test
	public void massDeleteTest() {
		Study study = studyService.insert(testData.get(2));
		Study study1 = studyService.insert(testData.get(1));
		StudyProtocol studyProtocol = new StudyProtocol();
		studyProtocol.setId(study.getId());
		studyProtocol.setProtocol("test protocol");
		studyProtocolService.insert(studyProtocol);

		Category category = new Category();
		category.setName(Integer.toString(new Object().hashCode()));
		categoryService.insert(category);

		Offer offer = new Offer();
		offer.setName(Integer.toString(new Object().hashCode()));
		offer.setPrice(99.999);
		offer.setCategorId(category.getId());
		offerService.insert(offer);

		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(study.getId());
		studyOfferCart.setOfferId(offer.getId());
		studyOfferCartService.insert(studyOfferCart);

		StudyOfferCart studyOfferCart1 = new StudyOfferCart();
		studyOfferCart1.setPaid(false);
		studyOfferCart1.setStudyId(study1.getId());
		studyOfferCart1.setOfferId(offer.getId());
		studyOfferCartService.insert(studyOfferCart1);

		studyService.massDelete(new Integer[] { study.getId(), study1.getId() });
		Study studyFromDB = studyService.get(study.getId());
		Assert.assertNull("Could not delete study", studyFromDB);

		studyFromDB = studyService.get(study1.getId());
		Assert.assertNull("Could not delete study", studyFromDB);

		StudyProtocol studyProtocolFromDB = studyProtocolService.get(studyProtocol.getId());
		Assert.assertNull("Could not delete study protocol", studyProtocolFromDB);

		StudyOfferCart studyOfferCartFromDB = studyOfferCartService.get(studyOfferCart.getId());
		Assert.assertNull("Could not delete studyOfferCart", studyOfferCartFromDB);

		studyOfferCartFromDB = studyOfferCartService.get(studyOfferCart1.getId());
		Assert.assertNull("Could not delete studyOfferCart", studyOfferCartFromDB);
	}

	@Test
	public void getStudyByPersonIdTest() {
		Study study = studyService.insert(testData.get(1));
		testStudyForList.setId(study.getId());
		List<StudyForList> studiesForListFromDB = studyService.getStudyForListByPersonId(study.getPersonId());
		Assert.assertTrue("Number of returned rows is not correct", studiesForListFromDB.size() == 1);
		StudyForList studyForList = studiesForListFromDB.get(0);
		Assert.assertTrue("Returned studyForList is not correct", testStudyForList.equals(studyForList));
	}

	@Test
	public void getCountTest() {
		int numberBeforeInsert = studyService.getCount();
		studyService.insert(testData.get(1));
		int numberAfterInsert = studyService.getCount();
		Assert.assertTrue("Returned count of rows is not correct", numberBeforeInsert + 1 == numberAfterInsert);
		studyService.delete(testData.get(1).getId());
		int numberAfterDelete = studyService.getCount();
		Assert.assertTrue("Returned count of rows is not correct", numberBeforeInsert == numberAfterDelete);
	}

	@Test
	public void getWithPaginationTest() {
		studyService.insert(testData.get(1));
		studyService.insert(testData.get(2));
		int number = studyService.getCount();
		List<StudyForList> listFromDB = studyService.getWithPagination(0, number);
		Assert.assertTrue("Number of returned rows is not correct", listFromDB.size() == number);
		listFromDB = studyService.getWithPagination(number, 0);
		Assert.assertTrue("Number of returned rows is not correct", listFromDB.size() == 0);
	}

	@Test
	public void getWithNullFilterTest() {
		int number = studyService.getCount();
		List<StudyForList> listFromDB = studyService.getWithPagination(0, number, null);
		Assert.assertTrue("Without filter must get all rows", number == listFromDB.size());
	}

	@Test
	public void getWithEmptyFilterTest() {
		int number = studyService.getCount();
		StudyFilter studyFilter = new StudyFilter();
		List<StudyForList> listFromDB = studyService.getWithPagination(0, number, studyFilter);
		Assert.assertTrue("With empty filter must get all rows", number == listFromDB.size());
	}

	@Test
	public void getWithFilterTest() {
		StudyFilter studyFilter = new StudyFilter();
		studyFilter.setPermitted(testData.get(1).getPermitted());
		studyFilter.setPatientFirstName(person.getFirstName());
		studyFilter.setPatientMiddleName(person.getMiddleName());
		studyFilter.setPatientLastName(person.getLastName());
		studyFilter.setDoctorFirstName(person.getFirstName());
		studyFilter.setDoctorMiddleName(person.getMiddleName());
		studyFilter.setDoctorLastName(person.getLastName());
		studyFilter.setFrom(new java.sql.Timestamp(testData.get(1).getAppointmentDate().getTime() - 1000));
		studyFilter.setTo(new java.sql.Timestamp(testData.get(1).getAppointmentDate().getTime() + 1000));
		SortData sort = new SortData();
		sort.setColumn("appointment_date");
		sort.setOrder("ASC");
		studyFilter.setSort(sort);

		studyService.insert(testData.get(1));
		int number = studyService.getCount();
		List<StudyForList> listFromDB = studyService.getWithPagination(0, number, studyFilter);
		Assert.assertTrue("Number of returned rows is not correct", listFromDB.size() == 1);
		testStudyForList.setId(testData.get(1).getId());
		StudyForList studyForList = listFromDB.get(0);
		Assert.assertTrue("Work of filter is not correct", testStudyForList.equals(studyForList));
	}

}
