package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyProtocolDao;
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;
import com.andreidadushko.tomography2017.services.IStudyProtocolService;

@Service
public class StudyProtocolServiceImpl implements IStudyProtocolService {

	@Inject
	private IStudyProtocolDao studyProtocolDao;

	@Override
	public StudyProtocol get(Integer id) {
		if (id == null)
			return null;
		return studyProtocolDao.get(id);

	}

	@Override
	public void insert(StudyProtocol studyProtocol) {
		if (isValid(studyProtocol))
			studyProtocolDao.insert(studyProtocol);
		else
			throw new IllegalArgumentException();

	}

	@Override
	public void update(StudyProtocol studyProtocol) {
		if (isValid(studyProtocol))
			studyProtocolDao.update(studyProtocol);
		else
			throw new IllegalArgumentException();

	}

	@Override
	public void delete(Integer id) {

		studyProtocolDao.delete(id);

	}

	@Override//УБРАТЬ!
	public List<StudyProtocol> getAll() {

		return studyProtocolDao.getAll();

	}

	private boolean isValid(StudyProtocol studyProtocol) {
		if (studyProtocol == null)
			return false;
		if (studyProtocol.getProtocol() == null || studyProtocol.getId() == null)
			return false;
		return true;
	}

}
