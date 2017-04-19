package com.andreidadushko.tomography2017.dao.db.custom.models;

import java.sql.Timestamp;

public class StudyOfferCartForList {

	private Integer id;
	private String category;
	private String offer;
	private Double price;
	private Boolean paid;
	private Timestamp payDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public Timestamp getPayDate() {
		return payDate;
	}

	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}

	@Override
	public String toString() {
		return "StudyOfferCartForList [id=" + id + ", category=" + category + ", offer=" + offer + ", price=" + price
				+ ", paid=" + paid + ", payDate=" + payDate + "]";
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
		StudyOfferCartForList other = (StudyOfferCartForList) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (offer == null) {
			if (other.offer != null)
				return false;
		} else if (!offer.equals(other.offer))
			return false;
		if (paid == null) {
			if (other.paid != null)
				return false;
		} else if (!paid.equals(other.paid))
			return false;
		if (payDate == null) {
			if (other.payDate != null)
				return false;
		} else {
			payDate.setNanos(0);
			other.payDate.setNanos(0);
			if (!payDate.equals(other.payDate))
				return false;
		}
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

}
