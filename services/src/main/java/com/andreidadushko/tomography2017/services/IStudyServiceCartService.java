package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.datamodel.StudyServiceCart;

public interface IStudyServiceCartService {

	StudyServiceCart get(Integer id);

	@Transactional
	StudyServiceCart insert(StudyServiceCart studyServiceCart);

	@Transactional
	void update(StudyServiceCart studyServiceCart);	

	@Transactional
	void delete(Integer id);
	
	List<StudyServiceCart> getAll();
	
}
