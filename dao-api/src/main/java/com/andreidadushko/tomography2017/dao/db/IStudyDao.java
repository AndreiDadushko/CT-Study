package com.andreidadushko.tomography2017.dao.db;

import java.util.List;

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.datamodel.Study;

public interface IStudyDao extends IAbstractDao<Study> {
	
	List<StudyForList> getStudyForListByPersonId(Integer personId);

	List<StudyForList> getWithPagination(int offset, int limit);

	List<StudyForList> getWithPagination(int offset, int limit, StudyFilter studyFilter);
	
	void massDelete(Integer[] idArray);
	
}
