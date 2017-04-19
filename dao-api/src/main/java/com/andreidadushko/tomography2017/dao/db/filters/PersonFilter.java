package com.andreidadushko.tomography2017.dao.db.filters;

import java.sql.Timestamp;

public class PersonFilter {

	private String firstName;
	private String middleName;
	private String lastName;
	private String adress;

	private Timestamp from;
	private Timestamp to;

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

	public Timestamp getFrom() {
		return from;
	}

	public void setFrom(Timestamp from) {
		this.from = from;
	}

	public Timestamp getTo() {
		return to;
	}

	public void setTo(Timestamp to) {
		this.to = to;
	}

	public SortData getSort() {
		return sort;
	}

	public void setSort(SortData sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "PersonFilter [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", adress=" + adress + ", from=" + from + ", to=" + to + ", sort=" + sort + "]";
	}

}
