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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudyForList other = (StudyForList) obj;
		if (appointmentDate == null) {
			if (other.appointmentDate != null)
				return false;
		} else {
			appointmentDate.setNanos(0);
			other.appointmentDate.setNanos(0);
			if (!appointmentDate.equals(other.appointmentDate))
				return false;
		}
		if (doctorFirstName == null) {
			if (other.doctorFirstName != null)
				return false;
		} else if (!doctorFirstName.equals(other.doctorFirstName))
			return false;
		if (doctorLastName == null) {
			if (other.doctorLastName != null)
				return false;
		} else if (!doctorLastName.equals(other.doctorLastName))
			return false;
		if (doctorMiddleName == null) {
			if (other.doctorMiddleName != null)
				return false;
		} else if (!doctorMiddleName.equals(other.doctorMiddleName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patientFirstName == null) {
			if (other.patientFirstName != null)
				return false;
		} else if (!patientFirstName.equals(other.patientFirstName))
			return false;
		if (patientLastName == null) {
			if (other.patientLastName != null)
				return false;
		} else if (!patientLastName.equals(other.patientLastName))
			return false;
		if (patientMiddleName == null) {
			if (other.patientMiddleName != null)
				return false;
		} else if (!patientMiddleName.equals(other.patientMiddleName))
			return false;
		if (permitted == null) {
			if (other.permitted != null)
				return false;
		} else if (!permitted.equals(other.permitted))
			return false;
		return true;
	}
}
