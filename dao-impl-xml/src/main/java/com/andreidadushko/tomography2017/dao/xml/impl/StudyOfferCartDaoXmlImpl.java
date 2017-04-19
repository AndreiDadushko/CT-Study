package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.dao.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.dao.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Repository
public class StudyOfferCartDaoXmlImpl implements IStudyOfferCartDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public StudyOfferCart get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudyOfferCart insert(StudyOfferCart object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(StudyOfferCart object) {
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
	public List<StudyOfferCart> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudyOfferCartForList> getStudyOfferCartByStudyId(Integer studyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void massDelete(Integer[] studyIdArray) {
		// TODO Auto-generated method stub
		
	}

	private File getFile() {
		File file = new File(rootFolder + "person.xml");
		return file;
	}
}
