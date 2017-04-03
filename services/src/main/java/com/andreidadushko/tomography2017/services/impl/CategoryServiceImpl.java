package com.andreidadushko.tomography2017.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.ICategoryDao;
import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.services.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService{

	@Inject
	private ICategoryDao categoryDao;
	
	@Override
	public Category get(Integer id) {
		
		return categoryDao.get(id);
		
	}

	@Override
	public Category insert(Category category) {
		
		return categoryDao.insert(category);
		
	}

	@Override
	public void update(Category category) {

		categoryDao.update(category);
		
	}

	@Override
	public void delete(Integer id) {

		categoryDao.delete(id);
		
	}

	@Override
	public List<Category> getAll() {
		
		return categoryDao.getAll();
		
	}

	@Override
	public List<Category> getByParentId(Integer parentId) {
		List<Category> listFromDB=getAll();
		List<Category> result= new ArrayList<Category>();
		for (Iterator<Category> iterator = listFromDB.iterator(); iterator.hasNext();) {
			Category category = iterator.next();
			if (category.getId()==parentId) result.add(category);
		}
		return result;
	}
	
}
