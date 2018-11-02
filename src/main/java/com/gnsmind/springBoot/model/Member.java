package com.gnsmind.springBoot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Entity(name="member")
public class Member {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idmember;
	@NotEmpty(message = "Lütfen adınızı giriniz..")
	@Size(min=1, max=20)
	@Column(name="name")
	private String name;
	@NotEmpty(message = "Lütfen soyadınızı giriniz..")
	@Size(min=1, max=20)
	@Column(name="surname")
	private String surname;
	@Column(name = "email", nullable = false, unique = true)
	@Email(message = "Lütfen geçerli bir e-mail adresi giriniz!")
	@NotEmpty(message = "Lütfen bir e-mail adresi giriniz..")
	private String email;
	@Column(name="tel")
	private String tel;
	@NotEmpty(message = "Lütfen bulunduğunuz şehrin adını giriniz..")
	@Column(name="city")
	private String city;
	@NotEmpty(message = "Lütfen bulunduğunuz ülkenin adını giriniz..")
	@Column(name="country")
	private String country;
	@Column(name="web_link")
	private String web_link;
	@Column(name="adress")
	private String adress;
	@Column(name="validation")
	private boolean validation;
	@Column(name="member_type")
	private String member_type;
	@Column(name="created_date", nullable = false)
	private Date created_date;
	@Column(name="ip")
	private String ip;
	@Column(name = "confirmation_token")
	private String confirmationToken;
	@Column(name = "password")
	@NotEmpty(message = "Lütfen şifrenizi giriniz!")
	@Size(min=6, max=8)
	private String password;
	@Column(name = "password_confirm")
	@NotEmpty(message = "Lütfen şifrenizi tekrar giriniz!")
	@Size(min=6, max=8)
	private String password_confirm;
	@Column(name = "remember_me")
	private boolean remember_me;
	
	
	public Member() {
		super();
	}
	public Member(String name, String surname, String email, String tel,
			String city, String country, String web_link, String adress,
			boolean validation, String member_type, Date created_date, String ip) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.tel = tel;
		this.city = city;
		this.country = country;
		this.web_link = web_link;
		this.adress = adress;
		this.validation = validation;
		this.member_type = member_type;
		this.created_date = created_date;
		this.ip = ip;
	}
	public int getIdmember() {
		return idmember;
	}
	public void setIdmember(int idmember) {
		this.idmember = idmember;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getWeb_link() {
		return web_link;
	}
	public void setWeb_link(String web_link) {
		this.web_link = web_link;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public boolean getValidation() {
		return validation;
	}
	public void setValidation(boolean validation) {
		this.validation = validation;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getRemember_me() {
		return remember_me;
	}
	public void setRemember_me(boolean remember_me) {
		this.remember_me = remember_me;
	}
	
	public String getPassword_confirm() {
		return password_confirm;
	}
	public void setPassword_confirm(String password_confirm) {
		this.password_confirm = password_confirm;
	}
	@Override
	public String toString() {
		return "{\"Member\" : [{\"idmember\" : \"" + idmember + "\", \"name\" : \"" + name + "\", \"surname\" : \""
				+ surname + "\", \"email\" : \"" + email + "\", \"tel\" : \"" + tel + "\", \"city\" : \""
				+ city + "\", \"country\" : \"" + country + "\", \"web_link\" : \"" + web_link
				+ "\", \"adress\" : \"" + adress + "\", \"validation\" : \"" + validation
				+ "\", \"member_type\" : \"" + member_type + "\", \"created_date\" : \""
				+ created_date + "\", \"ip\" : \"" + ip + "\"}]}";
	}
	
	
}
