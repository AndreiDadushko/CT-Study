package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.impl.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.services.IStudyOfferCartService;
import com.andreidadushko.tomography2017.services.IStudyProtocolService;
import com.andreidadushko.tomography2017.services.IStudyService;

@Service
public class StudyServiceImpl implements IStudyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyServiceImpl.class);

	@Inject
	private IStudyDao studyDao;

	@Inject
	private IStudyProtocolService studyProtocolService;

	@Inject
	private IStudyOfferCartService studyOfferCartService;

	@Override
	public Study get(Integer id) {
		LOGGER.info("Get study with id = " + id);
		if (id == null)
			return null;
		return studyDao.get(id);
	}

	@Override
	public Integer getCount() {
		return studyDao.getCount();
	}

	@Override
	public Study insert(Study study) {
		isValid(study);
		studyDao.insert(study);
		LOGGER.info("Insert study with id = " + study.getId());
		return study;
	}

	@Override
	public void update(Study study) {
		if (isValid(study) && study.getId() != null) {
			studyDao.update(study);
			LOGGER.info("Update study with id = " + study.getId());
		} else
			throw new IllegalArgumentException("Could not update study without id");
	}

	@Override
	public void delete(Integer id) {
		studyProtocolService.delete(id);
		studyOfferCartService.massDelete(new Integer[] { id });
		studyDao.delete(id);
		LOGGER.info("Delete study with id = " + id);
	}

	@Override
	public void massDelete(Integer[] idArray) {
		studyProtocolService.massDelete(idArray);
		studyOfferCartService.massDelete(idArray);
		studyDao.massDelete(idArray);
		LOGGER.info("Delete studies with id = " + idArray);
	}

	@Override
	public List<StudyForList> getWithPagination(int offset, int limit) {
		List<StudyForList> list = studyDao.getWithPagination(offset, limit);
		LOGGER.info("Get list of studies for list with offset = {}, limit = {}", offset, limit);
		return list;
	}

	@Override
	public List<StudyForList> getWithPagination(int offset, int limit, StudyFilter studyFilter) {
		List<StudyForList> list = studyDao.getWithPagination(offset, limit, studyFilter);
		LOGGER.info("Get list of studies for list with offset = {}, limit = {} and filter = ", offset, limit,
				studyFilter);
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
			throw new IllegalArgumentException("Could not insert/update null");
		if (study.getAppointmentDate() == null || study.getPersonId() == null || study.getStaffId() == null)
			throw new IllegalArgumentException("Study must have appointment date, staff id and person id");
		if (study.getPermitted() == null)
			study.setPermitted(true);
		return true;
	}
}
