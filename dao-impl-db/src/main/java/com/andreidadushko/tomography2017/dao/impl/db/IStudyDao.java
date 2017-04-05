package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.datamodel.Study;

public interface IStudyDao extends IAbstractDao<Study>{
	
	List<StudyForList> getAllStudyForList();
		
	List<StudyForList> getStudyForListByPersonId(Integer personId);
}
