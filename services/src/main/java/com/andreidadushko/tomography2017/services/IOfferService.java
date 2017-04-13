package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.datamodel.Offer;

public interface IOfferService {

	Offer get(Integer id);

	@Transactional
	Offer insert(Offer offer);

	@Transactional
	void update(Offer offer);

	@Transactional
	void delete(Integer id);

	Integer getCount();
	
	List<Offer> getAll();
	
	List<Offer> getByCategoryId(Integer categoryId);

}
