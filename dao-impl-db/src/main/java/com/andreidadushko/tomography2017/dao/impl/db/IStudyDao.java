package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.datamodel.Study;

public interface IStudyDao {
	Study get(Integer id);

	Study insert(Study study);

	void update(Study study);	

	void delete(Integer id);
	
	List<Study> getAll();
}
