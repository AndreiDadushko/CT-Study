package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IOfferDao;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.services.IOfferService;

@Service
public class OfferServiceImpl implements IOfferService{
	
	@Inject
	private IOfferDao offerDao;

	@Override
	public Offer get(Integer id) {
		
		return offerDao.get(id);
		
	}

	@Override
	public Offer insert(Offer offer) {
		
		return offerDao.insert(offer);
		
	}

	@Override
	public void update(Offer offer) {

		offerDao.update(offer);
		
	}

	@Override
	public void delete(Integer id) {

		offerDao.delete(id);
		
	}

	@Override
	public List<Offer> getAll() {
		
		return offerDao.getAll();
		
	}

	
}
