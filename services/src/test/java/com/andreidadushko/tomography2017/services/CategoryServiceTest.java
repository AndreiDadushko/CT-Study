package com.andreidadushko.tomography2017.services;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.andreidadushko.tomography2017.datamodel.Category;

public class CategoryServiceTest extends AbstractTest {

	@Inject
	private ICategoryService categoryService;

	@Test
	public void insertGetTest() {
		Category category = new Category();
		category.setName("test category");
		category.setParentId(null);
		category = categoryService.insert(category);
		Category categoryFromDB = categoryService.get(category.getId());
		Assert.assertTrue("Returned data with null parent isn't correct", category.equals(categoryFromDB));

		category.setName("test category1111");
		category.setParentId(category.getId());
		category = categoryService.insert(category);
		categoryFromDB = categoryService.get(category.getId());
		Assert.assertTrue("Returned data with not null parent isn't correct", category.equals(categoryFromDB));
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

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void insertWrongParentIdTest() {
		Category category = new Category();
		category.setName("test category");
		category.setParentId(null);
		category = categoryService.insert(category);
		categoryService.delete(category.getId());

		Category category1 = new Category();
		category1.setName("test category111");
		category1.setParentId(category.getId());
		categoryService.insert(category1);
		Assert.fail("Could not insert category with parent id, that does not exist");
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

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void updateWrongParentIdTest() {
		Category category = new Category();
		category.setName("test category");
		category.setParentId(null);
		category = categoryService.insert(category);
		categoryService.delete(category.getId());

		Category category1 = new Category();
		category1.setName("test category111");
		category1.setParentId(null);
		category1 = categoryService.insert(category1);
		category1.setParentId(category.getId());
		categoryService.update(category1);
		Assert.fail("Could not update category's parent id, without existing parent");
	}

	@Test
	public void updateTest() {
		Category category = new Category();
		category.setName("test category");
		category.setParentId(null);
		category = categoryService.insert(category);

		Category category1 = new Category();
		category1.setName("test category111");
		category1.setParentId(null);
		category1 = categoryService.insert(category1);

		category1.setParentId(category.getId());
		categoryService.update(category1);

		Category categoryFromDB = categoryService.get(category1.getId());
		Assert.assertTrue("Updated data isn't correct", category1.equals(categoryFromDB));
	}

	@Test
	public void deleteTest() {
		Category category = new Category();
		category.setName("test category");
		category.setParentId(null);
		category = categoryService.insert(category);
		categoryService.delete(category.getId());

		Category categoryFromDB = categoryService.get(category.getId());
		Assert.assertNull("Could not delete data", categoryFromDB);
	}

	@Test
	public void getAllTest() {
		List<Category> categories = categoryService.getAll();
		int numberBeforeInsert = categories.size();
		Category category = new Category();
		category.setName("test category");
		category.setParentId(null);
		categoryService.insert(category);

		categories = categoryService.getAll();
		int numberAfterInsert = categories.size();
		Assert.assertTrue("Could not get all categories", numberBeforeInsert + 1 == numberAfterInsert);
	}
	
	@Test
	public void getByParentIdTest() {
		Category category = new Category();
		category.setName("test category");
		category.setParentId(null);
		category = categoryService.insert(category);

		Category category1 = new Category();
		category1.setName("test category111");
		category1.setParentId(category.getId());
		category1 = categoryService.insert(category1);
		
		Category category2 = new Category();
		category2.setName("test category222");
		category2.setParentId(category.getId());
		category2 = categoryService.insert(category2);
		List<Category> categoris = categoryService.getByParentId(category.getId());
		Assert.assertTrue("Could not get all subcategories", categoris.size()==2);
	}

}
