package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.webapp.models.CategoryModel;

public class CategoryEntityToModelConverter implements Converter<Category, CategoryModel> {

	@Override
	public CategoryModel convert(Category category) {
		CategoryModel categoryModel = new CategoryModel();
		categoryModel.setId(category.getId());
		categoryModel.setName(category.getName());
		categoryModel.setParentId(category.getParentId());
		return categoryModel;
	}

}
