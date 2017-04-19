package com.andreidadushko.tomography2017.dao.db;

import java.util.List;

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

public interface IStudyOfferCartDao extends IAbstractDao<StudyOfferCart> {

	List<StudyOfferCartForList> getStudyOfferCartByStudyId(Integer studyId);
	
	void massDelete(Integer[] studyIdArray);
}
