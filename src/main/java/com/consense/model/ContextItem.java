package com.consense.model;

import java.util.Date;
import java.util.List;

public class ContextItem {

	private Integer itemId;
	private Integer userId;
	private String 	type;
	private List<Parameter> params;
	private Date created;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<Parameter> getParams() {
		return params;
	}
	public void setParams(List<Parameter> params) {
		this.params = params;
	}

	
}
