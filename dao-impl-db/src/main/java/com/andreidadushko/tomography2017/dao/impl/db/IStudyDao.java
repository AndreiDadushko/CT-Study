package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.impl.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.datamodel.Study;

public interface IStudyDao extends IAbstractDao<Study> {

	List<StudyForList> getStudyForListByPersonId(Integer personId);

	List<StudyForList> getWithPagination(int offset, int limit);

	List<StudyForList> getWithPagination(int offset, int limit, StudyFilter studyFilter);
}
