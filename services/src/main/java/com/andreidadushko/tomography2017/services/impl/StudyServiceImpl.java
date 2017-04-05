package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.services.IStudyService;

@Service
public class StudyServiceImpl implements IStudyService {

	@Inject
	private IStudyDao studyDao;

	@Override
	public Study get(Integer id) {
		if (id == null)
			return null;
		return studyDao.get(id);

	}

	@Override
	public Study insert(Study study) {
		if (isValid(study))
			return studyDao.insert(study);
		else
			throw new IllegalArgumentException();

	}

	@Override
	public void update(Study study) {
		if (isValid(study) && study.getId() != null)
			studyDao.update(study);
		else
			throw new IllegalArgumentException();

	}

	@Override
	public void delete(Integer id) {

		studyDao.delete(id);

	}

	@Override//УБРАТЬ!
	public List<Study> getAll() {

		return studyDao.getAll();
	}

	@Override
	public List<StudyForList> getAllStudyForList() {

		return studyDao.getAllStudyForList();
	}

	@Override
	public List<StudyForList> getStudyForListByPersonId(Integer personId) {

		return studyDao.getStudyForListByPersonId(personId);
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
