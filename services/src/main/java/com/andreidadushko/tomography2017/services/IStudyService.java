package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.datamodel.Study;

public interface IStudyService {

	Study get(Integer id);

	@Transactional
	Study insert(Study study);

	@Transactional
	void update(Study study);	

	@Transactional
	void delete(Integer id);
	
	List<Study> getAll();
	
	List<StudyForList> getAllStudyForList();
	
	List<StudyForList> getStudyForListByPersonId(Integer personId);
	
}
