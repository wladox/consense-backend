package com.consense.model.context;

import java.util.Date;

public class ContextItem {

	public static final int TYPE_ACTIVITY = 1;
	public static final int TYPE_APPS = 2;
	public static final int TYPE_LOCATION = 3;
	public static final int TYPE_MUSIC = 4;
	public static final int TYPE_AUDIO = 5;
	public static final int TYPE_PEDOMETER = 6;
	
	
	public static final String COLUMN_ID 		= "id";
	public static final String COLUMN_TYPE 		= "type";
	public static final String COLUMN_USER_ID 	= "user_id";
	public static final String COLUMN_CREATED 	= "created";
	
	private Integer itemId;
	private Integer userId;
	private Integer	type;
	private Date created;
	
	public ContextItem() {
		
	}
	
//	public enum ContextType {
//		
//		ACTIVITY(1), APPS(2), LOCATION(3), MUSIC(4), AUDIO(5), PEDOMETER(6);
//		
//		private int id;
//		
//		private ContextType(int id) {
//			this.id = id;
//		}
//		
//		public int getId() {
//			return id;
//		}
//
//		public static String getValueById(int id) {
//			for (ContextType ct : ContextType.values())	{
//				if (ct.getId() == id)
//					return ct.toString();
//			}
//			return "";
//		}
//	}
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
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

	
}
