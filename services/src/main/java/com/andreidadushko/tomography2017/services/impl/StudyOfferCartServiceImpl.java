package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;
import com.andreidadushko.tomography2017.services.IStudyOfferCartService;

@Service
public class StudyOfferCartServiceImpl implements IStudyOfferCartService {

	@Inject
	private IStudyOfferCartDao studyServiceCartDao;

	@Override
	public StudyOfferCart get(Integer id) {

		return studyServiceCartDao.get(id);

	}

	@Override
	public StudyOfferCart insert(StudyOfferCart studyServiceCart) {

		return studyServiceCartDao.insert(studyServiceCart);

	}

	@Override
	public void update(StudyOfferCart studyServiceCart) {

		studyServiceCartDao.update(studyServiceCart);

	}

	@Override
	public void delete(Integer id) {

		studyServiceCartDao.delete(id);

	}

	@Override
	public List<StudyOfferCart> getAll() {

		return studyServiceCartDao.getAll();

	}

}
