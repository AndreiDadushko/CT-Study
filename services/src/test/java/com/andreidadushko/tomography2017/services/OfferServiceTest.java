package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

	private static List<Category> testData;

	@Before
	public void initializeTestData() {
		testData = new ArrayList<Category>();
		Category category = new Category();
		category.setName("test category");
		category.setParentId(null);
		category = categoryService.insert(category);
		testData.add(category);
	}

	@Test
	public void insertGetTest() {
		Offer offer = new Offer();
		offer.setName("test offer");
		offer.setPrice(56.65);
		offer.setCategorId(testData.get(0).getId());
		offer = offerService.insert(offer);
		Offer offerFromDB = offerService.get(offer.getId());
		Assert.assertTrue("Returned data isn't correct", offer.equals(offerFromDB));
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
		Assert.fail("Could not insert emty offer");
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void insertWrongCategorIdTest() {
		categoryService.delete(testData.get(0).getId());

		Offer offer = new Offer();
		offer.setName("test offer");
		offer.setPrice(56.65);
		offer.setCategorId(testData.get(0).getId());
		offer = offerService.insert(offer);
		Assert.fail("Could not insert offer without existing category");
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
		Assert.fail("Could not update emty offer");
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void updateWrongParentIdTest() {
		Category category = new Category();
		category.setName("test category wrong id");
		category.setParentId(null);
		category = categoryService.insert(category);
		categoryService.delete(category.getId());

		Offer offer = new Offer();
		offer.setName("test offer");
		offer.setPrice(56.65);
		offer.setCategorId(testData.get(0).getId());
		offer = offerService.insert(offer);

		offer.setCategorId(category.getId());
		offerService.update(offer);
		Assert.fail("Could not update offer's category id, without existing category");
	}

	@Test
	public void updateTest() {
		Category category = new Category();
		category.setName("test category wrong id");
		category.setParentId(null);
		category = categoryService.insert(category);

		Offer offer = new Offer();
		offer.setName("test offer");
		offer.setPrice(56.65);
		offer.setCategorId(testData.get(0).getId());
		offer = offerService.insert(offer);

		offer.setCategorId(category.getId());
		offerService.update(offer);
		Offer offerFromDB = offerService.get(offer.getId());
		Assert.assertTrue("Updated data isn't correct", offer.equals(offerFromDB));
	}

	@Test
	public void deleteTest() {
		Offer offer = new Offer();
		offer.setName("test offer");
		offer.setPrice(56.65);
		offer.setCategorId(testData.get(0).getId());
		offer = offerService.insert(offer);
		offerService.delete(offer.getId());
		Offer offerFromDB = offerService.get(offer.getId());
		Assert.assertNull("Could not delete data", offerFromDB);
	}

	@Test
	public void getAllTest() {
		List<Offer> offers = offerService.getAll();
		int numberBeforeInsert = offers.size();
		Offer offer = new Offer();
		offer.setName("test offer");
		offer.setPrice(56.65);
		offer.setCategorId(testData.get(0).getId());
		offer = offerService.insert(offer);
		offers = offerService.getAll();
		int numberAfterInsert = offers.size();
		Assert.assertTrue("Could not get all offers", numberBeforeInsert + 1 == numberAfterInsert);
	}

}
