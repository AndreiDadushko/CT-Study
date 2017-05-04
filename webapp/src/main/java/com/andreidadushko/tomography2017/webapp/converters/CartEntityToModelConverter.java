package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;
import com.andreidadushko.tomography2017.webapp.models.StudyOfferCartModel;

public class CartEntityToModelConverter implements Converter<StudyOfferCart, StudyOfferCartModel> {

	@Override
	public StudyOfferCartModel convert(StudyOfferCart studyOfferCart) {
		StudyOfferCartModel cartModel = new StudyOfferCartModel();
		cartModel.setId(studyOfferCart.getId());
		cartModel.setPaid(studyOfferCart.getPaid());
		cartModel.setPayDate(studyOfferCart.getPayDate() == null ? null : studyOfferCart.getPayDate().getTime());
		cartModel.setStudyId(studyOfferCart.getStudyId());
		cartModel.setOfferId(studyOfferCart.getOfferId());
		return cartModel;
	}

}
