package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.ICategoryDao;
import com.andreidadushko.tomography2017.datamodel.Category;

@Repository
public class CategoryDaoXmlImpl extends AbstractDaoXmlImpl<Category> implements ICategoryDao {

	@Override
	public File getFile() {
		File file = new File(rootFolder + "category.xml");
		return file;
	}

	@Override
	public boolean idEquals(Category object, Integer id) {
		return object.getId().equals(id);
	}

	@Override
	public void setId(Category object, int id) {
		object.setId(id);
	}

	@Override
	public void updateObject(List<Category> list, Category object) {
		for (Category category : list) {
			if (category.getId().equals(object.getId())) {
				category.setName(object.getName());
				category.setParentId(object.getParentId());
				break;
			}
		}
	}

}
