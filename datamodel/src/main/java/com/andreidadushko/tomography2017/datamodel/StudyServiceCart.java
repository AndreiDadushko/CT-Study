package com.andreidadushko.tomography2017.datamodel;

import java.util.Date;

public class StudyServiceCart {

	private Integer id;
	private Boolean paid;
	private Date payDate;
	private Integer studyId;
	private Integer serviceId;

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

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String toString() {
		return "StudyServiceCart [id=" + id + ", paid=" + paid + ", payDate=" + payDate + ", studyId=" + studyId
				+ ", serviceId=" + serviceId + "]";
	}

}
