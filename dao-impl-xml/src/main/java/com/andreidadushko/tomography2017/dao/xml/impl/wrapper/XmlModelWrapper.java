package com.andreidadushko.tomography2017.dao.xml.impl.wrapper;

import java.util.ArrayList;
import java.util.List;

public class XmlModelWrapper<MODEL> {

    private List<MODEL> rows = new ArrayList<>();

    private Integer lastId;

	public List<MODEL> getRows() {
		return rows;
	}

	public void setRows(List<MODEL> rows) {
		this.rows = rows;
	}

	public Integer getLastId() {
		return lastId;
	}

	public void setLastId(Integer lastId) {
		this.lastId = lastId;
	}
	
}