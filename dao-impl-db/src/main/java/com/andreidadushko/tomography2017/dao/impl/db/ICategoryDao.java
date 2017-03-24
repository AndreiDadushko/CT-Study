package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.datamodel.Category;

public interface ICategoryDao {
	Category get(Integer id);

	Category insert(Category category);

	void update(Category category);	

	void delete(Integer id);
	
	List<Category> getAll();
}
