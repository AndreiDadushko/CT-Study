package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.ICategoryDao;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.Category;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Repository
public class CategoryDaoXmlImpl implements ICategoryDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Category get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category insert(Category object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Category object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private void writeNewData(File file, XmlModelWrapper<Category> obj) {
		try {
			xstream.toXML(obj, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private File getFile() {
		File file = new File(rootFolder + "person.xml");
		return file;
	}
}
