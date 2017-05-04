package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.webapp.models.CartForListModel;

public class CartForListEntityToModelConverter implements Converter<StudyOfferCartForList, CartForListModel> {

	@Override
	public CartForListModel convert(StudyOfferCartForList studyOfferCartForList) {
		CartForListModel cartForListModel = new CartForListModel();
		cartForListModel.setId(studyOfferCartForList.getId());
		cartForListModel.setCategory(studyOfferCartForList.getCategory());
		cartForListModel.setOffer(studyOfferCartForList.getOffer());
		cartForListModel.setPrice(studyOfferCartForList.getPrice());
		cartForListModel.setPaid(studyOfferCartForList.getPaid());
		cartForListModel.setPayDate(
				studyOfferCartForList.getPayDate() == null ? null : studyOfferCartForList.getPayDate().getTime());
		return cartForListModel;
	}

}
