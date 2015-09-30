package com.consense.model.context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class LocationItem extends ContextItem {

public static final String TABLE_NAME = "state_location";
	
	public static final String COLUMN_ACCURACY 	= "accuracy";
	public static final String COLUMN_LAT 		= "lat";
	public static final String COLUMN_LONG 		= "long";
	public static final String COLUMN_SPEED 	= "speed";
	
	private float 	accuracy;
	private double 	latitude;
	private double 	longitude;
	private float 	speed;
	
	public LocationItem() {
		super();
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public static LocationItem getFromResult(ResultSet rs) {
		LocationItem item = new LocationItem();
		try {
			item.setItemId(rs.getInt("context_state_id"));
			item.setType(rs.getInt("type"));
			item.setCreated(rs.getTimestamp("created"));
			item.setAccuracy(rs.getFloat(COLUMN_ACCURACY));
			item.setLatitude(rs.getDouble(COLUMN_LAT));
			item.setLongitude(rs.getDouble(COLUMN_LONG));
			item.setSpeed(rs.getFloat(COLUMN_SPEED));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public static LocationItem getFromJSON(JSONObject json) {

		LocationItem item = new LocationItem();
		//item.setItemId(json.getInt("context_state_id"));
		item.setType(ContextItem.TYPE_LOCATION);
		item.setCreated(new Date(json.getLong("created")));
		JSONArray params = json.getJSONArray("params");
		for (int i = 0; i < params.length(); i++) {
			JSONObject object = params.getJSONObject(i);
			if (object.getString("name").equals(LocationItem.COLUMN_ACCURACY)) 
				item.setAccuracy((float)object.getDouble("value"));
			else if (object.getString("name").equals(LocationItem.COLUMN_LAT))
				item.setLatitude(object.getDouble("value"));
			else if (object.getString("name").equals(LocationItem.COLUMN_LONG))
				item.setLongitude(object.getDouble("value"));
			else if (object.getString("name").equals(LocationItem.COLUMN_SPEED))
				item.setSpeed((float)object.getDouble("value"));
		}
		
		return item;
	}

	
}
