package com.andreidadushko.tomography2017.dao.impl.db.custom.models;

import java.sql.Timestamp;

public class StudyForList {

	private Integer id;
	private Timestamp appointmentDate;
	private Boolean permitted;
	private String patientFirstName;
	private String patientMiddleName;
	private String patientLastName;
	private String doctorFirstName;
	private String doctorMiddleName;
	private String doctorLastName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Timestamp appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

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

	@Override
	public String toString() {
		return "StudyForList [id=" + id + ", appointmentDate=" + appointmentDate + ", permitted=" + permitted
				+ ", patientFirstName=" + patientFirstName + ", patientMiddleName=" + patientMiddleName
				+ ", patientLastName=" + patientLastName + ", doctorFirstName=" + doctorFirstName
				+ ", doctorMiddleName=" + doctorMiddleName + ", doctorLastName=" + doctorLastName + "]";
	}
	
}
