package com.consense.model.context;

import java.sql.ResultSet;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

public class PedometerItem extends ContextItem {

	public static final String TABLE_NAME = "state_pedometer";
	public static final String COLUMN_STEPS = "steps";

	private Integer steps;
	
	public PedometerItem() {
		super();
	}

	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}

	public static ContextItem getFromResult(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	public static PedometerItem getFromJSON(JSONObject json) {
		PedometerItem item = new PedometerItem();
		//item.setItemId(json.getInt("context_state_id"));
		item.setType(ContextItem.TYPE_PEDOMETER);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(json.getLong("created"));
		item.setCreated(c.getTime());
		JSONArray params = json.getJSONArray("params");
		for (int i = 0; i < params.length(); i++) {
			JSONObject object = params.getJSONObject(i);
			if (object.getString("name").equals(COLUMN_STEPS)) 
				item.setSteps(object.getInt("value"));
		}
		
		return item;
	}
	
	

}
