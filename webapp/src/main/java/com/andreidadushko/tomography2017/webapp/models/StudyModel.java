package com.andreidadushko.tomography2017.webapp.models;

public class StudyModel {

	private Integer id;
	private Long appointmentDate;
	private Boolean permitted;
	private Integer personId;
	private Integer staffId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Long appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Boolean getPermitted() {
		return permitted;
	}

	public void setPermitted(Boolean permitted) {
		this.permitted = permitted;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

}
