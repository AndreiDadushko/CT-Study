package com.andreidadushko.tomography2017.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.db.IOfferDao;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.services.IOfferService;

@Service
public class OfferServiceImpl implements IOfferService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);

	@Inject
	private IOfferDao offerDao;

	@Override
	public Offer get(Integer id) {
		LOGGER.info("Get offer with id = " + id);
		if (id == null)
			return null;
		return offerDao.get(id);
	}

	@Override
	public Integer getCount() {
		return offerDao.getCount();
	}

	@Override
	public Offer insert(Offer offer) {
		isValid(offer);
		offerDao.insert(offer);
		LOGGER.info("Insert offer with id = " + offer.getId());
		return offer;
	}

	@Override
	public void update(Offer offer) {
		if (isValid(offer) && offer.getId() != null) {
			offerDao.update(offer);
			LOGGER.info("Update offer with id = " + offer.getId());
		} else
			throw new IllegalArgumentException("Could not update offer without id");
	}

	@Override
	public void delete(Integer id) {
		offerDao.delete(id);
		LOGGER.info("Delete offer with id = " + id);
	}

	@Override
	public List<Offer> getAll() {
		List<Offer> list = offerDao.getAll();
		LOGGER.info("Get list of all offers");
		return list;
	}

	@Override
	public List<Offer> getByCategoryId(Integer categoryId) {
		List<Offer> listFromDB = getAll();
		List<Offer> result = new ArrayList<Offer>();
		for (Iterator<Offer> iterator = listFromDB.iterator(); iterator.hasNext();) {
			Offer offer = iterator.next();
			if (categoryId == null) {
				if (offer.getCategorId() == null)
					result.add(offer);
			} else if (categoryId.equals(offer.getCategorId()))
				result.add(offer);
		}
		LOGGER.info("Get list of all offers with category id = " + categoryId);
		return result;
	}

	private boolean isValid(Offer offer) {
		if (offer == null)
			throw new IllegalArgumentException("Could not insert/update null");
		if (offer.getName() == null || offer.getNameEn() == null || offer.getPrice() == null
				|| offer.getCategorId() == null)
			throw new IllegalArgumentException("Offer must have name, name_en, price and category id");
		return true;
	}
}
