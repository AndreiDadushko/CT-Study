package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IStudyDao;
import com.andreidadushko.tomography2017.dao.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Repository
public class StudyDaoXmlImpl implements IStudyDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Study get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Study insert(Study object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Study object) {
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
	public List<Study> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudyForList> getStudyForListByPersonId(Integer personId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudyForList> getWithPagination(int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudyForList> getWithPagination(int offset, int limit, StudyFilter studyFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void massDelete(Integer[] idArray) {
		// TODO Auto-generated method stub
		
	}

	private File getFile() {
		File file = new File(rootFolder + "person.xml");
		return file;
	}
}
