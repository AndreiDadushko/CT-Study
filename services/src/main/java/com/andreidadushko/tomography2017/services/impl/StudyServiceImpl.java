package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.services.IStudyService;

@Service
public class StudyServiceImpl implements IStudyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyServiceImpl.class);

	@Inject
	private IStudyDao studyDao;

	@Override
	public Study get(Integer id) {
		LOGGER.info("Get study with id = " + id);
		if (id == null)
			return null;
		return studyDao.get(id);

	}

	@Override
	public Study insert(Study study) {
		if (isValid(study)) {
			studyDao.insert(study);
			LOGGER.info("Insert study with id = " + study.getId());
			return study;
		} else
			throw new IllegalArgumentException();

	}

	@Override
	public void update(Study study) {
		if (isValid(study) && study.getId() != null) {
			studyDao.update(study);
			LOGGER.info("Update study with id = " + study.getId());
		} else
			throw new IllegalArgumentException();

	}

	@Override
	public void delete(Integer id) {

		studyDao.delete(id);
		LOGGER.info("Delete study with id = " + id);
	}

	@Override
	public List<StudyForList> getAllStudyForList() {
		List<StudyForList> list = studyDao.getAllStudyForList();
		LOGGER.info("Get list of all study for list");
		return list;
	}

	@Override
	public List<StudyForList> getStudyForListByPersonId(Integer personId) {
		List<StudyForList> list = studyDao.getStudyForListByPersonId(personId);
		LOGGER.info("Get list of all study for list with person id = " + personId);
		return list;
	}

	private boolean isValid(Study study) {
		if (study == null)
			return false;
		if (study.getAppointmentDate() == null || study.getPersonId() == null || study.getStaffId() == null)
			return false;
		if (study.getPermitted() == null)
			study.setPermitted(true);
		study.getAppointmentDate().setNanos(0);
		return true;
	}
}
