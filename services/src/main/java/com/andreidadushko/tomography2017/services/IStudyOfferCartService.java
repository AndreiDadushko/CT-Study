package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

public interface IStudyOfferCartService {

	StudyOfferCart get(Integer id);

	@Transactional
	StudyOfferCart insert(StudyOfferCart studyOfferCart);

	@Transactional
	void update(StudyOfferCart studyOfferCart);

	@Transactional
	void delete(Integer id);
	
	List<StudyOfferCartForList> getStudyOfferCartByStudyId(Integer studyId);

}
