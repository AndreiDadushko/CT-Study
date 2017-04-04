package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IOfferDao;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.services.IOfferService;

@Service
public class OfferServiceImpl implements IOfferService {

	@Inject
	private IOfferDao offerDao;

	@Override
	public Offer get(Integer id) {
		if (id == null)
			return null;
		return offerDao.get(id);

	}

	@Override
	public Offer insert(Offer offer) {
		if (isValid(offer))
			return offerDao.insert(offer);
		else
			throw new IllegalArgumentException();

	}

	@Override
	public void update(Offer offer) {
		if (isValid(offer) && offer.getId() != null)
			offerDao.update(offer);
		else
			throw new IllegalArgumentException();

	}

	@Override
	public void delete(Integer id) {

		offerDao.delete(id);

	}

	@Override
	public List<Offer> getAll() {

		return offerDao.getAll();

	}

	private boolean isValid(Offer offer) {
		if (offer == null)
			return false;
		if (offer.getName() == null || offer.getPrice() == null || offer.getCategorId() == null)
			return false;
		return true;
	}
}
