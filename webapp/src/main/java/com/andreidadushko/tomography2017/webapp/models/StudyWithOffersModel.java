package com.andreidadushko.tomography2017.webapp.models;

public class StudyWithOffersModel {

	private IntegerModel studyId;
	private IntegerModel[] offerIdArray;

	public IntegerModel getStudyId() {
		return studyId;
	}

	public void setStudyId(IntegerModel studyId) {
		this.studyId = studyId;
	}

	public IntegerModel[] getOfferIdArray() {
		return offerIdArray;
	}

	public void setOfferIdArray(IntegerModel[] offerIdArray) {
		this.offerIdArray = offerIdArray;
	}

}
