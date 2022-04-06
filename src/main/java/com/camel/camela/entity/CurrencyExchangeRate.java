package com.camel.camela.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "to_inr_currencies", query = "select c from CurrencyExchangeRate c where c.toCurr = 'INR'")
public class CurrencyExchangeRate {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String fromCurr;
	private String toCurr;
	private Integer rate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromCurr() {
		return fromCurr;
	}

	public void setFromCurr(String fromCurr) {
		this.fromCurr = fromCurr;
	}

	public String getToCurr() {
		return toCurr;
	}

	public void setToCurr(String toCurr) {
		this.toCurr = toCurr;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "CurrencyExchangeRate [id=" + id + ", fromCurr=" + fromCurr + ", toCurr=" + toCurr + ", rate=" + rate + "]";
	}
	
}
