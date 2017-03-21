package com.andreidadushko.tomography2017.datamodel;



public class Service {

	Integer id;
	String name;
	Double price;
	Integer categorId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCategorId() {
		return categorId;
	}

	public void setCategorId(Integer categorId) {
		this.categorId = categorId;
	}

}
