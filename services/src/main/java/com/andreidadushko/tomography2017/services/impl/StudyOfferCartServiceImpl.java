package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;
import com.andreidadushko.tomography2017.services.IStudyOfferCartService;

@Service
public class StudyOfferCartServiceImpl implements IStudyOfferCartService {

	@Inject
	private IStudyOfferCartDao studyServiceCartDao;

	@Override
	public StudyOfferCart get(Integer id) {
		if (id == null)
			return null;
		return studyServiceCartDao.get(id);

	}

	@Override
	public StudyOfferCart insert(StudyOfferCart studyServiceCart) {
		if (isValid(studyServiceCart))
			return studyServiceCartDao.insert(studyServiceCart);
		else
			throw new IllegalArgumentException();

	}

	@Override
	public void update(StudyOfferCart studyServiceCart) {
		if (isValid(studyServiceCart) && studyServiceCart.getId() != null)
			studyServiceCartDao.update(studyServiceCart);
		else
			throw new IllegalArgumentException();

	}

	@Override
	public void delete(Integer id) {

		studyServiceCartDao.delete(id);

	}
	
	@Override
	public List<StudyOfferCartForList> getStudyOfferCartByStudyId(Integer studyId) {
	
		return studyServiceCartDao.getStudyOfferCartByStudyId(studyId);
		
	}

	private boolean isValid(StudyOfferCart studyOfferCart) {
		if (studyOfferCart == null)
			return false;
		if (studyOfferCart.getPaid() == null || studyOfferCart.getStudyId() == null
				|| studyOfferCart.getOfferId() == null)
			return false;
		if (studyOfferCart.getPaid() == null)
			studyOfferCart.setPaid(false);
		if (studyOfferCart.getPayDate() != null)
			studyOfferCart.getPayDate().setNanos(0);
		return true;
	}
}
