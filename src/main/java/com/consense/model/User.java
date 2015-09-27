package com.consense.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	
	public static final String SCHEMA = "network";
	public static final String TABLE_NAME = "user";
	
	public static final String COLUMN_USER_ID 	= "id";
	public static final String COLUMN_USERNAME	= "username";
	public static final String COLUMN_EMAIL		= "email";
	public static final String COLUMN_NAME		= "name";
	public static final String COLUMN_SURNAME	= "surname";
	public static final String COLUMN_PASSWORD 	= "password";
	public static final String COLUMN_BIRTHDAY	= "birthday";
	public static final String COLUMN_SEX		= "sex";
	

	private Integer userId;
	private String 	username;
	private String 	email;
	private String 	name;
	private String	surname;
	
	private String 	password;
	
	private Date 	birthday;
	private String 	sex;
	private String	image;
	private List<UserFeature> features;
	
	private HashMap<Integer, Integer> accessRules;
	
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

	public HashMap<Integer, Integer> getAccessRules() {
		return accessRules;
	}

	public void setAccessRules(HashMap<Integer, Integer> accessRules) {
		this.accessRules = accessRules;
	}
	
	
	
	
}
