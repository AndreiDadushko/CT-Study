package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

public interface IStudyOfferCartService {

	StudyOfferCart get(Integer id);

	@Transactional
	StudyOfferCart insert(StudyOfferCart studyOfferCart);

	@Transactional
	void update(StudyOfferCart studyOfferCart);

	@Transactional
	void delete(Integer id);

	@Transactional
	void massDelete(Integer[] studyIdArray);

	@Transactional
	void massInsert(Study study, List<Offer> offer);
	
	List<StudyOfferCartForList> getCartByStudyId(Integer studyId);
}
