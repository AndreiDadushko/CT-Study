package com.andreidadushko.tomography2017.dao.impl.db.filters;

public class SortData {

	private String column;
	private String order;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "SortData [column=" + column + ", order=" + order + "]";
	}

}
