package com.andreidadushko.tomography2017.datamodel;

import java.util.Date;

public class StudyProtocol {

	private Integer id;
	private String protocol;
	private Date creationDate;

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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "StudyProtocol [id=" + id + ", protocol=" + protocol + ", creationDate=" + creationDate + "]";
	}

}
