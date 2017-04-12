package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

public interface IStudyOfferCartDao extends IAbstractDao<StudyOfferCart> {

	List<StudyOfferCartForList> getStudyOfferCartByStudyId(Integer studyId);
	
	void massDelete(Integer[] studyIdArray);
}
