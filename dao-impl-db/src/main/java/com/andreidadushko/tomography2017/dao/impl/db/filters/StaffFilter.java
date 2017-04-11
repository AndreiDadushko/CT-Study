package com.andreidadushko.tomography2017.dao.impl.db.filters;

import java.sql.Timestamp;

public class StaffFilter {

	private String firstName;
	private String middleName;
	private String lastName;
	private String department;
	private String position;

	private Timestamp startFrom;
	private Timestamp startTo;
	private Timestamp endFrom;
	private Timestamp endTo;

	private SortData sort;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Timestamp getStartFrom() {
		return startFrom;
	}

	public void setStartFrom(Timestamp startFrom) {
		this.startFrom = startFrom;
	}

	public Timestamp getStartTo() {
		return startTo;
	}

	public void setStartTo(Timestamp startTo) {
		this.startTo = startTo;
	}

	public Timestamp getEndFrom() {
		return endFrom;
	}

	public void setEndFrom(Timestamp endFrom) {
		this.endFrom = endFrom;
	}

	public Timestamp getEndTo() {
		return endTo;
	}

	public void setEndTo(Timestamp endTo) {
		this.endTo = endTo;
	}

	public SortData getSort() {
		return sort;
	}

	public void setSort(SortData sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "StaffFilter [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", department=" + department + ", position=" + position + ", startFrom=" + startFrom + ", startTo="
				+ startTo + ", endFrom=" + endFrom + ", endTo=" + endTo + ", sort=" + sort + "]";
	}

}
