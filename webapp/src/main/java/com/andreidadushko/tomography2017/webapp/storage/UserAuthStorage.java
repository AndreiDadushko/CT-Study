package com.andreidadushko.tomography2017.webapp.storage;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class UserAuthStorage {

	private Integer id;
	private List<String> positions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getPositions() {
		return positions;
	}

	public void setPositions(List<String> positions) {
		this.positions = positions;
	}

	@Override
	public String toString() {
		return "User with id=" + id;
	}

}
