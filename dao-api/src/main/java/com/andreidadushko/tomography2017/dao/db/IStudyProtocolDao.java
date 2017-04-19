package com.andreidadushko.tomography2017.dao.db;

import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

public interface IStudyProtocolDao extends IAbstractDao<StudyProtocol> {
	
	void massDelete(Integer[] idArray);
	
}
