package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.webapp.models.OfferFullModel;

public class OfferModelToEntityConverter implements Converter<OfferFullModel, Offer> {

	@Override
	public Offer convert(OfferFullModel offerFullModel) {
		Offer offer = new Offer();
		offer.setId(offerFullModel.getId());
		offer.setName(offerFullModel.getName());
		offer.setNameEn(offerFullModel.getNameEn());
		offer.setPrice(offerFullModel.getPrice());
		offer.setCategorId(offerFullModel.getCategorId());
		return offer;
	}

}
