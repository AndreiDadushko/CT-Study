package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.webapp.models.CategoryModel;

public class CategoryModelToEntityConverter implements Converter<CategoryModel, Category> {

	@Override
	public Category convert(CategoryModel categoryModel) {
		Category category = new Category();
		category.setId(categoryModel.getId());
		category.setName(categoryModel.getName());
		category.setParentId(categoryModel.getParentId());
		return category;
	}

}
