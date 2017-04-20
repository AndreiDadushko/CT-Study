package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IStudyDao;
import com.andreidadushko.tomography2017.dao.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.Study;

@Repository
public class StudyDaoXmlImpl extends AbstractDaoXmlImpl<Study> implements IStudyDao {

	@Override
	public File getFile() {
		File file = new File(rootFolder + "study.xml");
		return file;
	}

	@Override
	public boolean idEquals(Study object, Integer id) {
		return object.getId().equals(id);
	}

	@Override
	public void setId(Study object, int id) {
		object.setId(id);
	}

	@Override
	public void updateObject(List<Study> list, Study object) {
		for (Study study : list) {
			if (study.getId().equals(object.getId())) {
				study.setAppointmentDate(object.getAppointmentDate());
				study.setPermitted(object.getPermitted());
				study.setPersonId(object.getPersonId());
				study.setStaffId(object.getStaffId());
				break;
			}
		}
	}

	@Override
	public void massDelete(Integer[] idArray) {
		if (idArray != null && idArray.length != 0) {
			File file = getFile();
			XmlModelWrapper<Study> wrapper = (XmlModelWrapper<Study>) xstream.fromXML(file);
			List<Study> list = wrapper.getRows();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Study study = (Study) iterator.next();
				for (int i = 0; i < idArray.length; i++) {
					if (study.getId().equals(idArray[i])) {
						iterator.remove();
						break;
					}
				}
			}
			writeNewData(file, wrapper);
		}
	}

	@Override
	public List<StudyForList> getStudyForListByPersonId(Integer personId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<StudyForList> getWithPagination(int offset, int limit) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<StudyForList> getWithPagination(int offset, int limit, StudyFilter studyFilter) {
		throw new UnsupportedOperationException();
	}

}
