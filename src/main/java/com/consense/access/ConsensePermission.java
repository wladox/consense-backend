package com.consense.access;

public class ConsensePermission {

	private Integer permId;
	private String 	name;
	private Integer categoryId;
	
	public enum PermissionName {
		MUSIC, EDUCATION, SPORT, FOOD, ACTIVITY, APPS, CONTACTS, EVENTS
	}
	
	public ConsensePermission() {
		
	}

	public Integer getPermId() {
		return permId;
	}

	public void setPermId(Integer permId) {
		this.permId = permId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
