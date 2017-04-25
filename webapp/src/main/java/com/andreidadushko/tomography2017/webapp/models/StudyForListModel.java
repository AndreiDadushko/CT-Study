package com.andreidadushko.tomography2017.webapp.models;

public class StudyForListModel {

	private Integer id;
	private Long appointmentDate;
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

}
