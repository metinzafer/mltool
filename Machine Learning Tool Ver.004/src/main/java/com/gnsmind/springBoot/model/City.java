package com.gnsmind.springBoot.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="city")
public class City implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idcity;
	@Column(name="name")
	private String name;
	@Column(name="area")
	private String area;
	@Column(name="country")
	private String country;
	
	public City() {
		super();
	}
	public City(String name, String area, String country) {
		super();
		this.name = name;
		this.area = area;
		this.country = country;
	}
	public int getIdcity() {
		return idcity;
	}
	public void setIdcity(int idcity) {
		this.idcity = idcity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String toString() {
		return "{\"City\" : [{\"idcity\" : \"" + idcity + "\", \"name\" : \"" + name + "\", \"area\" : \"" + area
				+ "\", \"country\" : \"" + country + "\"}]}";
	}
	
	
}
