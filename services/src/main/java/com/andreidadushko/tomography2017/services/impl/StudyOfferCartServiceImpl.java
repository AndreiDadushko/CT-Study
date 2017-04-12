package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;
import com.andreidadushko.tomography2017.services.IStudyOfferCartService;

@Service
public class StudyOfferCartServiceImpl implements IStudyOfferCartService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyOfferCartServiceImpl.class);

	@Inject
	private IStudyOfferCartDao studyOfferCartDao;

	@Override
	public StudyOfferCart get(Integer id) {

		LOGGER.info("Get studyOfferCart with id = " + id);
		if (id == null)
			return null;
		return studyOfferCartDao.get(id);
	}

	@Override
	public StudyOfferCart insert(StudyOfferCart studyOfferCart) {
		if (isValid(studyOfferCart)) {
			studyOfferCartDao.insert(studyOfferCart);
			LOGGER.info("Insert studyOfferCart with id = " + studyOfferCart.getId());
			return studyOfferCart;
		} else
			throw new IllegalArgumentException();

	}

	@Override
	public void update(StudyOfferCart studyOfferCart) {
		if (isValid(studyOfferCart) && studyOfferCart.getId() != null) {
			studyOfferCartDao.update(studyOfferCart);
			LOGGER.info("Update studyOfferCart with id = " + studyOfferCart.getId());
		} else
			throw new IllegalArgumentException();

	}

	@Override
	public void delete(Integer id) {

		studyOfferCartDao.delete(id);
		LOGGER.info("Delete studyOfferCart with id = " + id);
	}

	@Override
	public List<StudyOfferCartForList> getStudyOfferCartByStudyId(Integer studyId) {

		List<StudyOfferCartForList> list = studyOfferCartDao.getStudyOfferCartByStudyId(studyId);
		LOGGER.info("Get list of all studyOfferCart");
		return list;
	}

	@Override
	public void massDelete(Integer[] studyIdArray) {

		studyOfferCartDao.massDelete(studyIdArray);
		LOGGER.info("Delete studyOfferCart with study id = " + studyIdArray);
	}

	@Override
	public void massInsert(Study study, List<Offer> offer) {
		if (study != null && offer != null) {
			int studyId = study.getId();
			for (int i = 0; i < offer.size(); i++) {
				if (offer.get(i) != null) {
					StudyOfferCart studyOfferCart = new StudyOfferCart();
					studyOfferCart.setStudyId(studyId);
					studyOfferCart.setOfferId(offer.get(i).getId());
					insert(studyOfferCart);
				}
			}
		}
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
