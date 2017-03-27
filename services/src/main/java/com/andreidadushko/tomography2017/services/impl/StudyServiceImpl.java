package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyDao;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.services.IStudyService;

@Service
public class StudyServiceImpl implements IStudyService {

	@Inject
	private IStudyDao studyDao;

	@Override
	public Study get(Integer id) {

		return studyDao.get(id);

	}

	@Override
	public Study insert(Study study) {

		return studyDao.insert(study);

	}

	@Override
	public void update(Study study) {

		studyDao.update(study);

	}

	@Override
	public void delete(Integer id) {

		studyDao.delete(id);

	}

	@Override
	public List<Study> getAll() {

		return studyDao.getAll();
	}

}