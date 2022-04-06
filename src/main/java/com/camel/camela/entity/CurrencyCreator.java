package com.camel.camela.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CurrencyCreator {

	@Id
	//@GeneratedValue
	private Integer id;
	private String creator;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "CurrencyCreator [id=" + id + ", creator=" + creator + "]";
	}

}
