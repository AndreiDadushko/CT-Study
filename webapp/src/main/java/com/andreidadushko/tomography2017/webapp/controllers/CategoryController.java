package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.services.ICategoryService;
import com.andreidadushko.tomography2017.webapp.models.CategoryModel;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Inject
	private ICategoryService categoryService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Category category = categoryService.get(id);
		CategoryModel convertedCategory = null;
		if (category != null) {
			convertedCategory = categoryEntity2CategoryModel(category);
		}
		return new ResponseEntity<CategoryModel>(convertedCategory, HttpStatus.OK);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		Integer result = categoryService.getCount();
		return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody CategoryModel categoryModel) {
		Category category = categoryModel2CategoryEntity(categoryModel);
		try {
			categoryService.insert(category);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<IntegerModel>(new IntegerModel(category.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody CategoryModel categoryModel) {
		Category category = categoryModel2CategoryEntity(categoryModel);
		try {
			categoryService.update(category);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		categoryService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/root", method = RequestMethod.GET)
	public ResponseEntity<?> getRootCategories() {
		List<Category> list = categoryService.getRootCategories();
		List<CategoryModel> convertedList = new ArrayList<CategoryModel>();
		for (Category category : list) {
			convertedList.add(categoryEntity2CategoryModel(category));
		}
		return new ResponseEntity<List<CategoryModel>>(convertedList, HttpStatus.OK);
	}

	@RequestMapping(value = "/parent/{parentId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByParentId(@PathVariable Integer parentId) {
		List<Category> list = categoryService.getByParentId(parentId);
		List<CategoryModel> convertedList = new ArrayList<CategoryModel>();
		for (Category category : list) {
			convertedList.add(categoryEntity2CategoryModel(category));
		}
		return new ResponseEntity<List<CategoryModel>>(convertedList, HttpStatus.OK);
	}

	private CategoryModel categoryEntity2CategoryModel(Category category) {
		CategoryModel categoryModel = new CategoryModel();
		categoryModel.setId(category.getId());
		categoryModel.setName(category.getName());
		categoryModel.setParentId(category.getParentId());
		return categoryModel;
	}

	private Category categoryModel2CategoryEntity(CategoryModel categoryModel) {
		Category category = new Category();
		category.setId(categoryModel.getId());
		category.setName(categoryModel.getName());
		category.setParentId(categoryModel.getParentId());
		return category;
	}
}
