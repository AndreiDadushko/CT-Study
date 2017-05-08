package com.andreidadushko.tomography2017.webapp.models;

public class OfferFullModel {

	private Integer id;
	private String name;
	private String nameEn;
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

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
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
