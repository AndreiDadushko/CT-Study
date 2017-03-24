package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

public interface IStudyProtocolDao {
	StudyProtocol get(Integer id);

	void insert(StudyProtocol studyProtocol);

	void update(StudyProtocol studyProtocol);	

	void delete(Integer id);
	
	List<StudyProtocol> getAll();
}
