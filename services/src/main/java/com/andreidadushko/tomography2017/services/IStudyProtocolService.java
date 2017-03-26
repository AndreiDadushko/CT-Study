package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

public interface IStudyProtocolService {

	StudyProtocol get(Integer id);

	@Transactional
	void insert(StudyProtocol studyProtocol);

	@Transactional
	void update(StudyProtocol studyProtocol);	

	@Transactional
	void delete(Integer id);
	
	List<StudyProtocol> getAll();
	
}
