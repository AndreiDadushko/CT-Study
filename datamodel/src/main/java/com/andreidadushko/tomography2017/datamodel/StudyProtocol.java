package com.andreidadushko.tomography2017.datamodel;

import java.sql.Timestamp;

public class StudyProtocol {

	private Integer id;
	private String protocol;
	private Timestamp creationDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "StudyProtocol [id=" + id + ", protocol=" + protocol + ", creationDate=" + creationDate + "]";
	}

}
