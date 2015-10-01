package com.consense.access;

import java.util.Date;

public class UserAssignment {

	private Integer userId;
	private Integer relatedWith;
	private Date created;
	private Integer roleId;
	
	public UserAssignment() {

	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRelatedWith() {
		return relatedWith;
	}

	public void setRelatedWith(Integer relatedWith) {
		this.relatedWith = relatedWith;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	
}
