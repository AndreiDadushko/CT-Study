package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.webapp.models.OfferModel;

public class OfferModelToEntityConverter implements Converter<OfferModel, Offer> {

	@Override
	public Offer convert(OfferModel offerModel) {
		Offer offer = new Offer();
		offer.setId(offerModel.getId());
		offer.setName(offerModel.getName());
		offer.setPrice(offerModel.getPrice());
		offer.setCategorId(offerModel.getCategorId());
		return offer;
	}

}
