package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

public interface IStudyOfferCartDao {
	
	StudyOfferCart get(Integer id);

	StudyOfferCart insert(StudyOfferCart studyOfferCart);

	void update(StudyOfferCart studyOfferCart);	

	void delete(Integer id);
	
	List<StudyOfferCart> getAll();
	
}
