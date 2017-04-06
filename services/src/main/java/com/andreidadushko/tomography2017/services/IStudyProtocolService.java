package com.andreidadushko.tomography2017.services;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

public interface IStudyProtocolService {

	StudyProtocol get(Integer id);

	@Transactional
	StudyProtocol insert(StudyProtocol studyProtocol);

	@Transactional
	void update(StudyProtocol studyProtocol);

	@Transactional
	void delete(Integer id);

}
