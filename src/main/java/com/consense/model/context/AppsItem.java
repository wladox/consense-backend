package com.consense.model.context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

public class AppsItem extends ContextItem {

	public static final String TABLE_NAME = "state_apps";
	
	public static final String COLUMN_NAMES = "names";
	
	private String names;
	
	public AppsItem() {
		super();
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public static AppsItem getFromResult(ResultSet rs) {
		
		AppsItem item = new AppsItem();
		try {
//			item.setItemId(rs.getInt("id"));
			item.setType(rs.getInt("type"));
			item.setCreated(rs.getTimestamp("created"));
			item.setNames(rs.getString(COLUMN_NAMES));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public static AppsItem getFromJSON(JSONObject json) {
		AppsItem item = new AppsItem();
		//item.setItemId(json.getInt("context_state_id"));
		item.setType(ContextItem.TYPE_APPS);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(json.getLong("created"));
		item.setCreated(c.getTime());
		JSONArray params = json.getJSONArray("params");
		for (int i = 0; i < params.length(); i++) {
			JSONObject object = params.getJSONObject(i);
			if (object.getString("name").equals(AppsItem.COLUMN_NAMES)) 
				item.setNames(object.getString("value"));
		}
		
		
		return item;
	}
	
}
