package com.andreidadushko.tomography2017.webapp.models;

import com.andreidadushko.tomography2017.dao.db.filters.SortData;

public class StaffFilterModel {
	private String firstName;
	private String middleName;
	private String lastName;
	private String department;
	private String position;

	private Long startFrom;
	private Long startTo;
	private Long endFrom;
	private Long endTo;

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

	public Long getStartFrom() {
		return startFrom;
	}

	public void setStartFrom(Long startFrom) {
		this.startFrom = startFrom;
	}

	public Long getStartTo() {
		return startTo;
	}

	public void setStartTo(Long startTo) {
		this.startTo = startTo;
	}

	public Long getEndFrom() {
		return endFrom;
	}

	public void setEndFrom(Long endFrom) {
		this.endFrom = endFrom;
	}

	public Long getEndTo() {
		return endTo;
	}

	public void setEndTo(Long endTo) {
		this.endTo = endTo;
	}

	public SortData getSort() {
		return sort;
	}

	public void setSort(SortData sort) {
		this.sort = sort;
	}

}
