package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;
import com.andreidadushko.tomography2017.webapp.models.StudyOfferCartModel;

public class CartModelToEntityConverter implements Converter<StudyOfferCartModel, StudyOfferCart> {

	@Override
	public StudyOfferCart convert(StudyOfferCartModel cartModel) {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setId(cartModel.getId());
		studyOfferCart.setPaid(cartModel.getPaid());
		studyOfferCart
				.setPayDate(cartModel.getPayDate() == null ? null : new java.sql.Timestamp(cartModel.getPayDate()));
		studyOfferCart.setStudyId(cartModel.getStudyId());
		studyOfferCart.setOfferId(cartModel.getOfferId());
		return studyOfferCart;
	}

}
