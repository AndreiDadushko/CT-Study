package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.datamodel.Offer;

public interface IOfferDao {
	
	Offer get(Integer id);

	Offer insert(Offer offer);

	void update(Offer offer);	

	void delete(Integer id);
	
	List<Offer> getAll();
	
}
