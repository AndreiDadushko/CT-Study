package com.andreidadushko.tomography2017.webapp.models;

import java.util.List;

public class StudyWithOffersModel {

	private StudyModel studyModel;
	private List<OfferModel> offerModel;

	public StudyModel getStudyModel() {
		return studyModel;
	}

	public void setStudyModel(StudyModel studyModel) {
		this.studyModel = studyModel;
	}

	public List<OfferModel> getOfferModel() {
		return offerModel;
	}

	public void setOfferModel(List<OfferModel> offerModel) {
		this.offerModel = offerModel;
	}

}
