package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Category;

public class CategoryServiceTest extends AbstractTest {

	@Inject
	private ICategoryService categoryService;

	private List<Category> testData;

	@Before
	public void initializeTestData() {
		testData = new ArrayList<Category>();

		Category category0 = new Category();
		category0.setName(Integer.toString(new Object().hashCode()));
		testData.add(category0);

		Category category1 = new Category();
		category1.setName(Integer.toString(new Object().hashCode()));
		testData.add(category1);

		Category category2 = new Category();
		category2.setName(Integer.toString(new Object().hashCode()));
		testData.add(category2);
	}

	@Test
	public void getTest() {
		Category category = categoryService.insert(testData.get(0));
		Category categoryFromDB = categoryService.get(category.getId());
		Assert.assertTrue("Returned category with null parent is not correct", category.equals(categoryFromDB));

		testData.get(1).setParentId(category.getId());
		category = categoryService.insert(testData.get(1));
		categoryFromDB = categoryService.get(category.getId());
		Assert.assertTrue("Returned category with parent is not correct", category.equals(categoryFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertNullTest() {
		categoryService.insert(null);
		Assert.fail("Could not insert null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertEmptyCategoryTest() {
		Category category = new Category();
		categoryService.insert(category);
		Assert.fail("Could not insert empty category");
	}

	@Test
	public void insertTest() {
		Category category = categoryService.insert(testData.get(0));
		Category categoryFromDB = categoryService.get(category.getId());
		Assert.assertTrue("Inserted category with null parent is not correct", category.equals(categoryFromDB));

		testData.get(1).setParentId(category.getId());
		category = categoryService.insert(testData.get(1));
		categoryFromDB = categoryService.get(category.getId());
		Assert.assertTrue("Inserted category with parent is not correct", category.equals(categoryFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateNullTest() {
		categoryService.update(null);
		Assert.fail("Could not update null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateEmptyCategoryTest() {
		Category category = new Category();
		categoryService.update(category);
		Assert.fail("Could not update empty category");
	}

	@Test
	public void updateTest() {
		categoryService.insert(testData.get(0));
		categoryService.insert(testData.get(1));
		testData.get(1).setParentId(testData.get(0).getId());
		categoryService.update(testData.get(1));
		Category categoryFromDB = categoryService.get(testData.get(1).getId());
		Assert.assertTrue("Updated data is not correct", testData.get(1).equals(categoryFromDB));
	}

	@Test
	public void deleteTest() {
		categoryService.insert(testData.get(0));
		categoryService.delete(testData.get(0).getId());
		Category categoryFromDB = categoryService.get(testData.get(0).getId());
		Assert.assertNull("Could not delete data", categoryFromDB);
	}

	@Test
	public void getCountTest() {
		int numberBeforeInsert = categoryService.getCount();
		categoryService.insert(testData.get(0));
		int numberAfterInsert = categoryService.getCount();
		Assert.assertTrue("Returned count of rows is not correct", numberBeforeInsert + 1 == numberAfterInsert);
		categoryService.delete(testData.get(0).getId());
		int numberAfterDelete = categoryService.getCount();
		Assert.assertTrue("Returned count of rows is not correct", numberBeforeInsert == numberAfterDelete);
	}

	@Test
	public void getRootCategoriesTest() {
		int numberBeforeInsert = categoryService.getByParentId(null).size();
		categoryService.insert(testData.get(0));
		int numberAfterInsert = categoryService.getByParentId(null).size();
		Assert.assertTrue("Returned after insert count of rows is not correct",
				numberBeforeInsert + 1 == numberAfterInsert);
		categoryService.delete(testData.get(0).getId());
		int numberAfterDelete = categoryService.getByParentId(null).size();
		Assert.assertTrue("Returned after delete count of rows is not correct",
				numberBeforeInsert == numberAfterDelete);
	}

	@Test
	public void getByParentIdTest() {
		categoryService.insert(testData.get(0));
		testData.get(1).setParentId(testData.get(0).getId());
		categoryService.insert(testData.get(1));
		testData.get(2).setParentId(testData.get(0).getId());
		categoryService.insert(testData.get(2));

		List<Category> categoris = categoryService.getByParentId(testData.get(0).getId());
		Assert.assertTrue("Could not get all subcategories", categoris.size() == 2);
	}

	@After
	public void destroyTestData() {
		categoryService.delete(testData.get(2).getId());
		categoryService.delete(testData.get(1).getId());
		categoryService.delete(testData.get(0).getId());
	}
}
