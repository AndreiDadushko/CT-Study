package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.datamodel.StudyServiceCart;

public interface IStudyServiceCartDao {
	StudyServiceCart get(Integer id);

	StudyServiceCart insert(StudyServiceCart studyServiceCart);

	void update(StudyServiceCart studyServiceCart);	

	void delete(Integer id);
	
	List<StudyServiceCart> getAll();
}
