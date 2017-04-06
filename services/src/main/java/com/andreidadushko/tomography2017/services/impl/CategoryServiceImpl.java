package com.andreidadushko.tomography2017.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.ICategoryDao;
import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.services.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Inject
	private ICategoryDao categoryDao;

	@Override
	public Category get(Integer id) {
		LOGGER.info("Get category with id = " + id);
		if (id == null)
			return null;
		return categoryDao.get(id);

	}

	@Override
	public Category insert(Category category) {
		if (isValid(category)) {
			categoryDao.insert(category);
			LOGGER.info("Insert category with id = " + category.getId());
			return category;
		} else
			throw new IllegalArgumentException();

	}

	@Override
	public void update(Category category) {
		if (isValid(category) && category.getId() != null) {
			categoryDao.update(category);
			LOGGER.info("Update category with id = " + category.getId());
		} else
			throw new IllegalArgumentException();

	}

	@Override
	public void delete(Integer id) {

		categoryDao.delete(id);
		LOGGER.info("Delete category with id = " + id);
	}

	@Override
	public List<Category> getAll() {
		List<Category> list = categoryDao.getAll();
		LOGGER.info("Get list of all categories");
		return list;

	}

	@Override
	public List<Category> getByParentId(Integer parentId) {
		List<Category> listFromDB = getAll();
		List<Category> result = new ArrayList<Category>();
		for (Iterator<Category> iterator = listFromDB.iterator(); iterator.hasNext();) {
			Category category = iterator.next();
			if (parentId.equals(category.getParentId()))
				result.add(category);
		}
		LOGGER.info("Get list of all categories with parent id = " + parentId);
		return result;
	}

	private boolean isValid(Category category) {
		if (category == null)
			return false;
		if (category.getName() == null)
			return false;
		return true;
	}
}
