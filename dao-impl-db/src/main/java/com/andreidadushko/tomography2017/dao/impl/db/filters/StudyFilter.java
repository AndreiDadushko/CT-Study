package com.andreidadushko.tomography2017.dao.impl.db.filters;

import java.sql.Timestamp;

public class StudyFilter {

	private Boolean permitted;
	private String patientFirstName;
	private String patientMiddleName;
	private String patientLastName;
	private String doctorFirstName;
	private String doctorMiddleName;
	private String doctorLastName;

	private Timestamp from;
	private Timestamp to;

	private SortData sort;

	public Boolean getPermitted() {
		return permitted;
	}

	public void setPermitted(Boolean permitted) {
		this.permitted = permitted;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientMiddleName() {
		return patientMiddleName;
	}

	public void setPatientMiddleName(String patientMiddleName) {
		this.patientMiddleName = patientMiddleName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getDoctorFirstName() {
		return doctorFirstName;
	}

	public void setDoctorFirstName(String doctorFirstName) {
		this.doctorFirstName = doctorFirstName;
	}

	public String getDoctorMiddleName() {
		return doctorMiddleName;
	}

	public void setDoctorMiddleName(String doctorMiddleName) {
		this.doctorMiddleName = doctorMiddleName;
	}

	public String getDoctorLastName() {
		return doctorLastName;
	}

	public void setDoctorLastName(String doctorLastName) {
		this.doctorLastName = doctorLastName;
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
		return "StudyFilter [permitted=" + permitted + ", patientFirstName=" + patientFirstName + ", patientMiddleName="
				+ patientMiddleName + ", patientLastName=" + patientLastName + ", doctorFirstName=" + doctorFirstName
				+ ", doctorMiddleName=" + doctorMiddleName + ", doctorLastName=" + doctorLastName + ", from=" + from
				+ ", to=" + to + ", sort=" + sort + "]";
	}

}
