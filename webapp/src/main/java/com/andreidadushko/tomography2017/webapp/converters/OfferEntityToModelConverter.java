package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.webapp.models.OfferModel;

public class OfferEntityToModelConverter implements Converter<Offer, OfferModel> {

	@Override
	public OfferModel convert(Offer offer) {
		OfferModel offerModel = new OfferModel();
		offerModel.setId(offer.getId());
		offerModel.setName(offer.getName());
		offerModel.setPrice(offer.getPrice());
		offerModel.setCategorId(offer.getCategorId());
		return offerModel;
	}

}
