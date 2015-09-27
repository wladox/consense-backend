package com.consense.model.context;

import java.util.Date;
import java.util.List;

public class ContextItem {

	private Integer itemId;
	private Integer userId;
	private String 	type;
//	private List<Parameter> params;
	private String params;
	private Date created;
	
	public enum ContextType {
		
		ACTIVITY(1), APPS(2), LOCATION(3);
		
		private int id;
		
		private ContextType(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}

		public static String getValueById(int id) {
			for (ContextType ct : ContextType.values())	{
				if (ct.getId() == id)
					return ct.toString();
			}
			return "";
		}
	}
	
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
//	public List<Parameter> getParams() {
//		return params;
//	}
//	public void setParams(List<Parameter> params) {
//		this.params = params;
//	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}


	
}
