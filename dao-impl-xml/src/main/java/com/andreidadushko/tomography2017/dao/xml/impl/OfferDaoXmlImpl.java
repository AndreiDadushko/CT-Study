package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IOfferDao;
import com.andreidadushko.tomography2017.datamodel.Offer;

@Repository
public class OfferDaoXmlImpl extends AbstractDaoXmlImpl<Offer> implements IOfferDao {

	@Override
	public File getFile() {
		File file = new File(rootFolder + "offer.xml");
		return file;
	}

	@Override
	public boolean idEquals(Offer object, Integer id) {
		return object.getId().equals(id);
	}

	@Override
	public void setId(Offer object, int id) {
		object.setId(id);
	}

	@Override
	public void updateObject(List<Offer> list, Offer object) {
		for (Offer offer : list) {
			if (offer.getId().equals(object.getId())) {
				offer.setName(object.getName());
				offer.setPrice(object.getPrice());
				offer.setCategorId(object.getCategorId());
				break;
			}
		}
	}

}
