package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IStudyProtocolDao;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

@Repository
public class StudyProtocolDaoXmlImpl extends AbstractDaoXmlImpl<StudyProtocol> implements IStudyProtocolDao {

	@Override
	public File getFile() {
		File file = new File(rootFolder + "study_protocol.xml");
		return file;
	}

	@Override
	public boolean idEquals(StudyProtocol object, Integer id) {
		return object.getId().equals(id);
	}

	@Override
	public void setId(StudyProtocol object, int id) {
		object.setCreationDate(new java.sql.Timestamp(new Date().getTime()));
	}

	@Override
	public void updateObject(List<StudyProtocol> list, StudyProtocol object) {
		for (StudyProtocol studyProtocol : list) {
			if (studyProtocol.getId().equals(object.getId())) {
				studyProtocol.setProtocol(object.getProtocol());
				studyProtocol.setCreationDate(new java.sql.Timestamp(new Date().getTime()));
				break;
			}
		}
	}

	@Override
	public void massDelete(Integer[] idArray) {
		if (idArray != null && idArray.length != 0) {
			File file = getFile();
			XmlModelWrapper<StudyProtocol> wrapper = (XmlModelWrapper<StudyProtocol>) xstream.fromXML(file);
			List<StudyProtocol> list = wrapper.getRows();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				StudyProtocol studyProtocol = (StudyProtocol) iterator.next();
				for (int i = 0; i < idArray.length; i++) {
					if (studyProtocol.getId().equals(idArray[i])) {
						iterator.remove();
						break;
					}
				}
			}
			writeNewData(file, wrapper);
		}
	}

}
