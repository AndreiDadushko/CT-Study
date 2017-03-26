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

		return studyProtocolDao.get(id);

	}

	@Override
	public void insert(StudyProtocol studyProtocol) {

		studyProtocolDao.insert(studyProtocol);

	}

	@Override
	public void update(StudyProtocol studyProtocol) {

		studyProtocolDao.update(studyProtocol);

	}

	@Override
	public void delete(Integer id) {

		studyProtocolDao.delete(id);

	}

	@Override
	public List<StudyProtocol> getAll() {

		return studyProtocolDao.getAll();

	}

}
