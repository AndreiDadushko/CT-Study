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
	public Integer getCount() {
		return studyProtocolDao.getCount();
	}

	@Override
	public StudyProtocol insert(StudyProtocol studyProtocol) {
		isValid(studyProtocol);
		studyProtocolDao.insert(studyProtocol);
		LOGGER.info("Insert study protocol with id = " + studyProtocol.getId());
		return studyProtocol;
	}

	@Override
	public void update(StudyProtocol studyProtocol) {
		isValid(studyProtocol);
		studyProtocolDao.update(studyProtocol);
		LOGGER.info("Update study protocol with id = " + studyProtocol.getId());
	}

	@Override
	public void delete(Integer id) {
		studyProtocolDao.delete(id);
		LOGGER.info("Delete study protocol with id = " + id);
	}

	@Override
	public void massDelete(Integer[] idArray) {
		studyProtocolDao.massDelete(idArray);
		LOGGER.info("Delete study protocol with id = " + idArray);
	}

	private boolean isValid(StudyProtocol studyProtocol) {
		if (studyProtocol == null)
			throw new IllegalArgumentException("Could not insert/update null");
		if (studyProtocol.getProtocol() == null || studyProtocol.getId() == null)
			throw new IllegalArgumentException("Study protocol must have id and protocol");
		return true;
	}

}
