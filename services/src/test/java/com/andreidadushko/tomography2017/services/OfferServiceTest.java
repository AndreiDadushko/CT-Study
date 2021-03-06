package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.datamodel.Offer;

public class OfferServiceTest extends AbstractTest {

	@Inject
	private IOfferService offerService;

	@Inject
	private ICategoryService categoryService;

	private List<Offer> testData;

	private Category category;
	private Category category1;

	@Before
	public void initializeTestData() {
		testData = new ArrayList<Offer>();
		category = new Category();
		category.setName(Integer.toString(new Object().hashCode()));
		category.setParentId(null);
		categoryService.insert(category);

		category1 = new Category();
		category1.setName(Integer.toString(new Object().hashCode()));

		Offer offer0 = new Offer();
		offer0.setName(Integer.toString(new Object().hashCode()));
		offer0.setNameEn(Integer.toString(new Object().hashCode()));
		offer0.setPrice(56.65);
		offer0.setCategorId(category.getId());
		testData.add(offer0);

		Offer offer1 = new Offer();
		offer1.setName(Integer.toString(new Object().hashCode()));
		offer1.setNameEn(Integer.toString(new Object().hashCode()));
		offer1.setPrice(56.65);
		offer1.setCategorId(category.getId());
		testData.add(offer1);
	}

	@Test
	public void getTest() {
		offerService.insert(testData.get(0));
		Offer offerFromDB = offerService.get(testData.get(0).getId());
		Assert.assertTrue("Returned data is not correct", testData.get(0).equals(offerFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertNullTest() {
		offerService.insert(null);
		Assert.fail("Could not insert null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertEmptyCategoryTest() {
		Offer offer = new Offer();
		offerService.insert(offer);
		Assert.fail("Could not insert empty offer");
	}

	@Test
	public void insertTest() {
		offerService.insert(testData.get(0));
		Offer offerFromDB = offerService.get(testData.get(0).getId());
		Assert.assertTrue("Returned data is not correct", testData.get(0).equals(offerFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateNullTest() {
		offerService.update(null);
		Assert.fail("Could not update null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateEmptyCategoryTest() {
		Offer offer = new Offer();
		offerService.update(offer);
		Assert.fail("Could not update empty offer");
	}

	@Test
	public void updateTest() {
		offerService.insert(testData.get(0));
		categoryService.insert(category1);
		testData.get(0).setCategorId(category1.getId());
		offerService.update(testData.get(0));
		Offer offerFromDB = offerService.get(testData.get(0).getId());
		Assert.assertTrue("Updated data is not correct", testData.get(0).equals(offerFromDB));
	}

	@Test
	public void deleteTest() {
		offerService.insert(testData.get(0));
		offerService.delete(testData.get(0).getId());
		Offer offerFromDB = offerService.get(testData.get(0).getId());
		Assert.assertNull("Could not delete data", offerFromDB);
	}

	@Test
	public void getAllTest() {
		List<Offer> offers = offerService.getAll();
		int numberBeforeInsert = offers.size();
		offerService.insert(testData.get(0));
		offers = offerService.getAll();
		int numberAfterInsert = offers.size();
		Assert.assertTrue("Could not get all offers", numberBeforeInsert + 1 == numberAfterInsert);
	}

	@Test
	public void getCountTest() {
		int numberBeforeInsert = offerService.getCount();
		offerService.insert(testData.get(0));
		int numberAfterInsert = offerService.getCount();
		Assert.assertTrue("Returned after insert count of rows is not correct",
				numberBeforeInsert + 1 == numberAfterInsert);
		offerService.delete(testData.get(0).getId());
		int numberAfterDelete = offerService.getCount();
		Assert.assertTrue("Returned after delete count of rows is not correct",
				numberBeforeInsert == numberAfterDelete);
	}

	@Test
	public void getByCategoryIdTest() {
		offerService.insert(testData.get(0));
		offerService.insert(testData.get(1));
		List<Offer> list = offerService.getByCategoryId(testData.get(1).getCategorId());
		Assert.assertTrue("Returned list of offers is not correct", list.size() == 2);
	}

	@After
	public void destroyTestData() {
		offerService.delete(testData.get(0).getId());
		offerService.delete(testData.get(1).getId());
		categoryService.delete(category.getId());
		categoryService.delete(category1.getId());
	}
}
