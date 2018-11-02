package com.gnsmind.springBoot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="ads")
public class Ads {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idads;
	@Column(name="member_Id")
	private int member_Id;
	@Column(name="photo_link")
	private String photo_link;
	@Column(name="content")
	private String content;
	@Column(name="map_Info")
	private String map_Info;
	@Column(name="ad_type")
	private String ad_type;
	@Column(name="validation")
	private int validation;
	@Column(name="city")
	private String city;
	@Column(name="country")
	private String country;
	@Column(name="rate")
	private int rate;
	@Column(name="ad_situation")
	private String ad_situation;
	@Column(name="created_date")
	private String created_date;
	
	public Ads() {
		super();
	}
	public Ads(int member_Id, String photo_link, String content,
			String map_Info, String ad_type, int validation, String city,
			String country, int rate, String ad_situation, String created_date) {
		super();
		this.member_Id = member_Id;
		this.photo_link = photo_link;
		this.content = content;
		this.map_Info = map_Info;
		this.ad_type = ad_type;
		this.validation = validation;
		this.city = city;
		this.country = country;
		this.rate = rate;
		this.ad_situation = ad_situation;
		this.created_date = created_date;
	}
	public int getIdads() {
		return idads;
	}
	public void setIdads(int idads) {
		this.idads = idads;
	}
	public int getMember_Id() {
		return member_Id;
	}
	public void setMember_Id(int member_Id) {
		this.member_Id = member_Id;
	}
	public String getPhoto_link() {
		return photo_link;
	}
	public void setPhoto_link(String photo_link) {
		this.photo_link = photo_link;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMap_Info() {
		return map_Info;
	}
	public void setMap_Info(String map_Info) {
		this.map_Info = map_Info;
	}
	public String getAd_type() {
		return ad_type;
	}
	public void setAd_type(String ad_type) {
		this.ad_type = ad_type;
	}
	public int getValidation() {
		return validation;
	}
	public void setValidation(int validation) {
		this.validation = validation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getAd_situation() {
		return ad_situation;
	}
	public void setAd_situation(String ad_situation) {
		this.ad_situation = ad_situation;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	
	

}
