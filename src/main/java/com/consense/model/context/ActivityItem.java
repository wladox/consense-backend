package com.consense.model.context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityItem extends ContextItem {

	public static final String TABLE_NAME = "state_activity";
	
	public static final String COLUMN_PROBABILITY = "probability";
	public static final String COLUMN_NAME = "name";
	
	private int probability;
	private String name;
	
	public ActivityItem() {
		super();
	}
	
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static ActivityItem getFromResult(ResultSet rs) {
		
		ActivityItem item = new ActivityItem();
		try {
			//item.setItemId(rs.getInt("id"));
			item.setType(rs.getInt("type"));
			item.setCreated(rs.getTimestamp("created"));
			item.setName(rs.getString(ActivityItem.COLUMN_NAME));
			item.setProbability(rs.getInt(ActivityItem.COLUMN_PROBABILITY));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	public static ActivityItem getFromJSON(JSONObject json) {
		
		ActivityItem item = new ActivityItem();
		//item.setItemId(json.getInt("context_state_id"));
		item.setType(ContextItem.TYPE_ACTIVITY);
		item.setCreated(new Date(json.getLong("created")));
		JSONArray params = json.getJSONArray("params");
		for (int i = 0; i < params.length(); i++) {
			JSONObject object = params.getJSONObject(i);
			if (object.getString("name").equals(ActivityItem.COLUMN_NAME)) 
				item.setName(object.getString("value"));
			else if (object.getString("name").equals(ActivityItem.COLUMN_PROBABILITY))
				item.setProbability(object.getInt("value"));
		}
		
		
		return item;
	}
	
}
