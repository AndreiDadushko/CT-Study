package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IStaffDao;
import com.andreidadushko.tomography2017.dao.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.dao.db.filters.StaffFilter;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Repository
public class StaffDaoXmlImpl implements IStaffDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Staff get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff insert(Staff object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Staff object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Staff> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getPositionsByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StaffForList> getWithPagination(int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StaffForList> getWithPagination(int offset, int limit, StaffFilter staffFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	private File getFile() {
		File file = new File(rootFolder + "person.xml");
		return file;
	}
}
