package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IOfferDao;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Repository
public class OfferDaoXmlImpl implements IOfferDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Offer get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Offer insert(Offer object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Offer object) {
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
	public List<Offer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private File getFile() {
		File file = new File(rootFolder + "person.xml");
		return file;
	}
}
