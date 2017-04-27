package com.andreidadushko.tomography2017.services.impl;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.dao.db.custom.models.StudyOfferCartForList;
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
		isValid(studyOfferCart);
		studyOfferCartDao.insert(studyOfferCart);
		LOGGER.info("Insert studyOfferCart with id = " + studyOfferCart.getId());
		return studyOfferCart;
	}

	@Override
	public void update(StudyOfferCart studyOfferCart) {
		if (isValid(studyOfferCart) && studyOfferCart.getId() != null) {
			studyOfferCartDao.update(studyOfferCart);
			LOGGER.info("Update studyOfferCart with id = " + studyOfferCart.getId());
		} else
			throw new IllegalArgumentException("Could not update studyOfferCart without id");
	}

	@Override
	public void delete(Integer id) {
		studyOfferCartDao.delete(id);
		LOGGER.info("Delete studyOfferCart with id = " + id);
	}

	@Override
	public List<StudyOfferCartForList> getCartByStudyId(Integer studyId) {
		List<StudyOfferCartForList> list = studyOfferCartDao.getStudyOfferCartByStudyId(studyId);
		LOGGER.info("Get list of all studyOfferCart");
		return list;
	}

	@Override
	public void massDelete(Integer[] studyIdArray) {
		studyOfferCartDao.massDelete(studyIdArray);
		LOGGER.info("Delete studyOfferCart with study id = " + Arrays.asList(studyIdArray));
	}

	@Override
	public void massInsert(Integer studyId, Integer[] offerIdArray) {
		if (studyId != null && offerIdArray != null) {
			for (int i = 0; i < offerIdArray.length; i++) {
				if (offerIdArray[i] != null) {
					StudyOfferCart studyOfferCart = new StudyOfferCart();
					studyOfferCart.setStudyId(studyId);
					studyOfferCart.setOfferId(offerIdArray[i]);
					insert(studyOfferCart);
				}
			}
		}
	}

	private boolean isValid(StudyOfferCart studyOfferCart) {
		if (studyOfferCart == null)
			throw new IllegalArgumentException("Could not insert/update null");
		if (studyOfferCart.getStudyId() == null || studyOfferCart.getOfferId() == null)
			throw new IllegalArgumentException("StudyOfferCart must have study id and offer id and paid information");
		if (studyOfferCart.getPaid() == null)
			studyOfferCart.setPaid(false);
		return true;
	}
}
