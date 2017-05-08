package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

public class StudyOfferCartServiceTest extends AbstractTest {

	@Inject
	private IStudyOfferCartService studyOfferCartService;

	@Inject
	private IOfferService offerService;

	@Inject
	private ICategoryService categoryService;

	@Inject
	private IStudyService studyService;

	@Inject
	private IStaffService staffService;

	@Inject
	private IPersonService personService;

	private List<StudyOfferCart> testData;

	private Person person;
	private Staff staff;
	private Study study1;
	private Study study2;
	private Category category;
	private Offer offer1;
	private Offer offer2;

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
		personService.insert(person);

		staff = new Staff();
		staff.setDepartment("РКД");
		staff.setPosition("Врач-рентгенолог");
		staff.setStartDate(new java.sql.Timestamp(new Date().getTime()));
		staff.setPersonId(person.getId());
		staffService.insert(staff);

		study1 = new Study();
		study1.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study1.setPermitted(true);
		study1.setPersonId(person.getId());
		study1.setStaffId(staff.getId());
		studyService.insert(study1);

		study2 = new Study();
		study2.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study2.setPermitted(true);
		study2.setPersonId(person.getId());
		study2.setStaffId(staff.getId());
		studyService.insert(study2);

		category = new Category();
		category.setName(Integer.toString(new Object().hashCode()));
		category.setParentId(null);
		categoryService.insert(category);

		offer1 = new Offer();
		offer1.setName(Integer.toString(new Object().hashCode()));
		offer1.setNameEn(Integer.toString(new Object().hashCode()));
		offer1.setPrice(56.65);
		offer1.setCategorId(category.getId());
		offerService.insert(offer1);

		offer2 = new Offer();
		offer2.setName(Integer.toString(new Object().hashCode()));
		offer2.setNameEn(Integer.toString(new Object().hashCode()));
		offer2.setPrice(56.65);
		offer2.setCategorId(category.getId());
		offerService.insert(offer2);

		testData = new ArrayList<StudyOfferCart>();
		StudyOfferCart studyOfferCart0 = new StudyOfferCart();
		studyOfferCart0.setPaid(false);
		studyOfferCart0.setStudyId(study1.getId());
		studyOfferCart0.setOfferId(offer1.getId());
		testData.add(studyOfferCart0);

		StudyOfferCart studyOfferCart1 = new StudyOfferCart();
		studyOfferCart1.setPaid(false);
		studyOfferCart1.setStudyId(study2.getId());
		studyOfferCart1.setOfferId(offer2.getId());
		testData.add(studyOfferCart1);

	}

	@Test
	public void getTest() {
		studyOfferCartService.insert(testData.get(0));
		StudyOfferCart studyOfferCartFromDB = studyOfferCartService.get(testData.get(0).getId());
		Assert.assertTrue("Returned data is not correct", testData.get(0).equals(studyOfferCartFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertNullTest() {
		studyOfferCartService.insert(null);
		Assert.fail("Could not insert null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertEmptyStudyOfferCartTest() {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCartService.insert(studyOfferCart);
		Assert.fail("Could not insert empty studyOfferCart");
	}

	@Test
	public void insertTest() {
		studyOfferCartService.insert(testData.get(0));
		StudyOfferCart studyOfferCartFromDB = studyOfferCartService.get(testData.get(0).getId());
		Assert.assertTrue("Returned data isn't correct", testData.get(0).equals(studyOfferCartFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateNullTest() {
		studyOfferCartService.update(null);
		Assert.fail("Could not update null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateEmptyStudyOfferCartTest() {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCartService.update(studyOfferCart);
		Assert.fail("Could not insert empty studyOfferCart");
	}

	@Test
	public void updateTest() {
		studyOfferCartService.insert(testData.get(0));

		testData.get(1).setId(testData.get(0).getId());
		studyOfferCartService.update(testData.get(1));
		StudyOfferCart studyOfferCartFromDB = studyOfferCartService.get(testData.get(1).getId());
		Assert.assertTrue("Updated data isn't correct", testData.get(1).equals(studyOfferCartFromDB));
	}

	@Test
	public void deleteTest() {
		studyOfferCartService.insert(testData.get(0));
		studyOfferCartService.delete(testData.get(0).getId());
		StudyOfferCart studyOfferCartFromDB = studyOfferCartService.get(testData.get(0).getId());
		Assert.assertNull("Could not delete data", studyOfferCartFromDB);
	}

	@Test
	public void massDeleteTest() {
		studyOfferCartService.insert(testData.get(0));
		studyOfferCartService.insert(testData.get(1));
		studyOfferCartService.massDelete(new Integer[] { testData.get(0).getStudyId(), testData.get(1).getStudyId() });
		StudyOfferCart studyOfferCartFromDB0 = studyOfferCartService.get(testData.get(0).getId());
		StudyOfferCart studyOfferCartFromDB1 = studyOfferCartService.get(testData.get(1).getId());
		Assert.assertTrue("Could not delete studyOfferCart",
				studyOfferCartFromDB0 == null && studyOfferCartFromDB1 == null);
	}

	@Test
	public void massInsertTest() {
		Integer[] offerIdArray = new Integer[]{offer1.getId(),offer2.getId()};
		studyOfferCartService.massInsert(study1.getId(), offerIdArray);
		List<StudyOfferCartForList> listFromDB = studyOfferCartService.getCartByStudyId(study1.getId());
		Assert.assertTrue("Could not insert studyOfferCart for each offer", listFromDB.size() == 2);
	}

	@Test
	public void getStudyOfferCartByStudyIdTest() {
		studyOfferCartService.insert(testData.get(0));
		List<StudyOfferCartForList> listFromDB = studyOfferCartService
				.getCartByStudyId(testData.get(0).getStudyId());
		Assert.assertTrue("Could not get all studyOfferCart by study id", listFromDB.size() == 1);
	}

	@Test
	public void getCartWithNullStudyIdTest() {
		studyOfferCartService.insert(testData.get(0));
		List<StudyOfferCartForList> listFromDB = studyOfferCartService.getCartByStudyId(null);
		Assert.assertTrue("Could not get studyOfferCart with null study id", listFromDB.size() == 0);
	}

	@After
	public void destroyTestData() {
		studyOfferCartService.delete(testData.get(0).getId());
		studyOfferCartService.delete(testData.get(1).getId());
		List<StudyOfferCartForList> listFromDB = studyOfferCartService.getCartByStudyId(study1.getId());
		for (StudyOfferCartForList studyOfferCartForList : listFromDB) {
			studyOfferCartService.delete(studyOfferCartForList.getId());
		}
		offerService.delete(offer1.getId());
		offerService.delete(offer2.getId());
		categoryService.delete(category.getId());
		studyService.delete(study1.getId());
		studyService.delete(study2.getId());
		staffService.delete(staff.getId());
		personService.delete(person.getId());
	}
}
