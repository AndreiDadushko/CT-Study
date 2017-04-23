package com.andreidadushko.tomography2017.webapp.models;

import com.andreidadushko.tomography2017.dao.db.filters.SortData;

public class PersonFilterModel {

	private String firstName;
	private String middleName;
	private String lastName;
	private String adress;

	private Long from;
	private Long to;

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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public SortData getSort() {
		return sort;
	}

	public void setSort(SortData sort) {
		this.sort = sort;
	}

}
