package com.consense.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	
	public static final String SCHEMA = "network";
	public static final String TABLE_NAME = "user";
	
	public static final String COLUMN_USER_ID = "id";
	public static final String COLUMN_NAME		= "username";
	public static final String COLUMN_EMAIL		= "email";
	public static final String COLUMN_PASSWORD 	= "password";
	public static final String COLUMN_BIRTHDAY	= "birthday";
	public static final String COLUMN_SEX		= "sex";
	

	private Integer userId;
	private String 	username;
	private String 	email;
	
	@JsonIgnore
	private String 	password;
	
	private Date 	birthday;
	private String 	sex;
	private String	image;
	private List<UserFeature> features;
	
	public User() {
		
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<UserFeature> getFeatures() {
		return features;
	}

	public void setFeatures(List<UserFeature> features) {
		this.features = features;
	}
	
	
	
}
