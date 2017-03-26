package com.andreidadushko.tomography2017.datamodel;

import java.sql.Timestamp;

public class Staff {

	private Integer id;
	private String department;
	private String position;
	private Timestamp startDate;
	private Timestamp endDate;
	private Integer personId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", department=" + department + ", position=" + position + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", personId=" + personId + "]";
	}

}
