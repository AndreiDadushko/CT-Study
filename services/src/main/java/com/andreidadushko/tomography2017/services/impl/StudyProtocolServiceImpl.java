package com.andreidadushko.tomography2017.services.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyProtocolDao;
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;
import com.andreidadushko.tomography2017.services.IStudyProtocolService;

@Service
public class StudyProtocolServiceImpl implements IStudyProtocolService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyProtocolServiceImpl.class);

	@Inject
	private IStudyProtocolDao studyProtocolDao;

	@Override
	public StudyProtocol get(Integer id) {
		LOGGER.info("Get study protocol with id = " + id);
		if (id == null)
			return null;
		return studyProtocolDao.get(id);

	}

	@Override
	public StudyProtocol insert(StudyProtocol studyProtocol) {
		if (isValid(studyProtocol)) {
			studyProtocolDao.insert(studyProtocol);
			LOGGER.info("Insert study protocol with id = " + studyProtocol.getId());
			return studyProtocol;
		} else
			throw new IllegalArgumentException();

	}

	@Override
	public void update(StudyProtocol studyProtocol) {
		if (isValid(studyProtocol)) {
			studyProtocolDao.update(studyProtocol);
			LOGGER.info("Update study protocol with id = " + studyProtocol.getId());
		} else
			throw new IllegalArgumentException();

	}

	@Override
	public void delete(Integer id) {

		studyProtocolDao.delete(id);
		LOGGER.info("Delete study protocol with id = " + id);
	}

	private boolean isValid(StudyProtocol studyProtocol) {
		if (studyProtocol == null)
			return false;
		if (studyProtocol.getProtocol() == null || studyProtocol.getId() == null)
			return false;
		return true;
	}

}
