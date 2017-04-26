package com.andreidadushko.tomography2017.webapp.models;

public class OfferModel {

	private Integer id;
	private String name;
	private Double price;
	private Integer categorId;
	
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
