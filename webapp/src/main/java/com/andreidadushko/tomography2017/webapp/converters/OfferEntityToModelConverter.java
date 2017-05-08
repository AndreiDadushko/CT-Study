package com.andreidadushko.tomography2017.webapp.converters;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.webapp.models.OfferModel;
import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

public class OfferEntityToModelConverter implements Converter<Offer, OfferModel> {

	@Inject
	private ApplicationContext context;
	
	@Override
	public OfferModel convert(Offer offer) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		System.out.println(userAuthStorage.getId());
		System.out.println(userAuthStorage.getPositions());
		System.out.println(userAuthStorage.getLocale());
		OfferModel offerModel = new OfferModel();
		offerModel.setId(offer.getId());
		offerModel.setName(offer.getName());
		offerModel.setPrice(offer.getPrice());
		offerModel.setCategorId(offer.getCategorId());
		return offerModel;
	}

}
