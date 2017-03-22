package com.andreidadushko.tomography2017.datamodel;

import java.util.Date;

public class Study {

	private Integer id;
	private Date appointmentDate;
	private Boolean permitted;
	private Integer personId;
	private Integer staffId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
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

	@Override
	public String toString() {
		return "Study [id=" + id + ", appointmentDate=" + appointmentDate + ", permitted=" + permitted + ", personId="
				+ personId + ", staffId=" + staffId + "]";
	}

}
