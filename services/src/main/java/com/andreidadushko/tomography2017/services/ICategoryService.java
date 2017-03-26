package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.datamodel.Category;

public interface ICategoryService {
	
	Category get(Integer id);

	@Transactional
	Category insert(Category category);

	@Transactional
	void update(Category category);

	@Transactional
	void delete(Integer id);

	List<Category> getAll();
	
}
