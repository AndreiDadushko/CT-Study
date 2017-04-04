package com.andreidadushko.tomography2017.services;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

public class StudyOfferCartTest extends AbstractTest {

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

	private static Study study1;	
	private static Integer freeStudyId;

	private static Offer offer;
	private static Integer freeOfferId;

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
		personService.insert(person);

		Staff staff = new Staff();
		staff.setDepartment("РКД");
		staff.setPosition("Лаборант");
		staff.setPersonId(person.getId());
		staffService.insert(staff);

		study1 = new Study();
		study1.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study1.setPermitted(true);
		study1.setPersonId(person.getId());
		study1.setStaffId(staff.getId());
		studyService.insert(study1);
		
		Study study2 = new Study();
		study2.setAppointmentDate(new java.sql.Timestamp(new Date().getTime()));
		study2.setPermitted(true);
		study2.setPersonId(person.getId());
		study2.setStaffId(staff.getId());
		studyService.insert(study2);
		studyService.delete(study2.getId());
		freeStudyId=study2.getId();

		Category category = new Category();
		category.setName("test category");
		category.setParentId(null);
		categoryService.insert(category);

		offer = new Offer();
		offer.setName("test offer");
		offer.setPrice(56.65);
		offer.setCategorId(category.getId());
		offerService.insert(offer);
		
		Offer offer1 = new Offer();
		offer1.setName("test offer1");
		offer1.setPrice(56.65);
		offer1.setCategorId(category.getId());
		offerService.insert(offer1);
		offerService.delete(offer1.getId());
		freeOfferId=offer1.getId();
	}

	@Test
	public void insertGetTest() {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(study1.getId());
		studyOfferCart.setOfferId(offer.getId());
		studyOfferCartService.insert(studyOfferCart);
		StudyOfferCart studyOfferCartFromDB = studyOfferCartService.get(studyOfferCart.getId());
		Assert.assertTrue("Returned data isn't correct", studyOfferCart.equals(studyOfferCartFromDB));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void insertNullTest() {
		studyOfferCartService.insert(null);
		Assert.fail("Could not insert null");	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void insertEmptyStudyOfferCartTest() {	
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCartService.insert(studyOfferCart);
		Assert.fail("Could not insert empty studyOfferCart");	
	}
	
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void insertWrongStudyIdTest() {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(freeStudyId);
		studyOfferCart.setOfferId(offer.getId());
		studyOfferCartService.insert(studyOfferCart);
		Assert.fail("Could not insert studyOfferCart without existing study");	
	}
	
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void insertWrongOfferIdTest() {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(study1.getId());
		studyOfferCart.setOfferId(freeOfferId);
		studyOfferCartService.insert(studyOfferCart);
		Assert.fail("Could not insert studyOfferCart without existing offer");	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateNullTest() {	
		studyOfferCartService.update(null);
		Assert.fail("Could not update null");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateEmptyStudyOfferCartTest() {	
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCartService.update(studyOfferCart);
		Assert.fail("Could not insert empty studyOfferCart");
	}
	
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void updateWrongStudyIdTest(){
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(study1.getId());
		studyOfferCart.setOfferId(offer.getId());
		studyOfferCartService.insert(studyOfferCart);
		
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(freeStudyId);
		studyOfferCart.setOfferId(offer.getId());
		studyOfferCartService.update(studyOfferCart);
		Assert.fail("Could not update studyOfferCart without existing study");	
	}
	
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void updateWrongOfferIdTest() {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(study1.getId());
		studyOfferCart.setOfferId(offer.getId());
		studyOfferCartService.insert(studyOfferCart);
		
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(study1.getId());
		studyOfferCart.setOfferId(freeOfferId);
		studyOfferCartService.update(studyOfferCart);
		Assert.fail("Could not update studyOfferCart without existing offer");	
	}
	
	@Test 
	public void updateTest() {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(study1.getId());
		studyOfferCart.setOfferId(offer.getId());
		studyOfferCartService.insert(studyOfferCart);
		
		studyOfferCart.setPaid(true);
		studyOfferCart.setPayDate(new java.sql.Timestamp(new Date().getTime()));
		studyOfferCartService.update(studyOfferCart);
		StudyOfferCart studyOfferCartFromDB = studyOfferCartService.get(studyOfferCart.getId());
		Assert.assertTrue("Updated data isn't correct", studyOfferCart.equals(studyOfferCartFromDB));
	}
	
	@Test 
	public void deleteTest() {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setPaid(false);
		studyOfferCart.setStudyId(study1.getId());
		studyOfferCart.setOfferId(offer.getId());
		studyOfferCartService.insert(studyOfferCart);
		studyOfferCartService.delete(studyOfferCart.getId());
		StudyOfferCart studyOfferCartFromDB = studyOfferCartService.get(studyOfferCart.getId());
		Assert.assertNull("Could not delete data", studyOfferCartFromDB);
	}

}
