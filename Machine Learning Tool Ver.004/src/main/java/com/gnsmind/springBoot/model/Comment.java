package com.gnsmind.springBoot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idcomment;
	@Column(name="content")
	@Size(min=0, max=300)
	private String content;
	@Column(name="member_Id")
	@NotNull
	private int member_Id;
	@Column(name="validation")
	private int validation;
	@Column(name="created_date", nullable = false)
	private Date created_date;
	
	public Comment() {
		super();
	}
	public Comment(String content, int member_Id, int validation) {
		super();
		this.content = content;
		this.member_Id = member_Id;
		this.validation = validation;
	}
	public int getIdcomment() {
		return idcomment;
	}
	public void setIdcomment(int idcomment) {
		this.idcomment = idcomment;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMember_Id() {
		return member_Id;
	}
	public void setMember_Id(int member_Id) {
		this.member_Id = member_Id;
	}
	public int getValidation() {
		return validation;
	}
	public void setValidation(int validation) {
		this.validation = validation;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
	
}
