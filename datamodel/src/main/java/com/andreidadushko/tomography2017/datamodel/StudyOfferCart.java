package com.andreidadushko.tomography2017.datamodel;

import java.sql.Timestamp;

public class StudyOfferCart {

	private Integer id;
	private Boolean paid;
	private Timestamp payDate;
	private Integer studyId;
	private Integer offerId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public Timestamp getPayDate() {
		return payDate;
	}

	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	
	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	@Override
	public String toString() {
		return "StudyOfferCart [id=" + id + ", paid=" + paid + ", payDate=" + payDate + ", studyId=" + studyId
				+ ", offerId=" + offerId + "]";
	}	

}
