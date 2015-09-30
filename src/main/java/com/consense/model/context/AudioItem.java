package com.consense.model.context;

import java.sql.ResultSet;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

public class AudioItem extends ContextItem {

	public static final String TABLE_NAME = "state_audio";
	
	public static final String COLUMN_PROBABILITY = "probability";
	public static final String COLUMN_CLASSIFICATION = "classification";
	
	private String classification;
	private int probability;
	
	public AudioItem() {
		super();
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public static ContextItem getFromResult(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	public static AudioItem getFromJSON(JSONObject json) {
		AudioItem item = new AudioItem();
		//item.setItemId(json.getInt("context_state_id"));
		item.setType(ContextItem.TYPE_AUDIO);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(json.getLong("created"));
		item.setCreated(c.getTime());
		JSONArray params = json.getJSONArray("params");
		for (int i = 0; i < params.length(); i++) {
			JSONObject object = params.getJSONObject(i);
			if (object.getString("name").equals(COLUMN_CLASSIFICATION)) 
				item.setClassification(object.getString("value"));
			else if (object.getString("name").equals(COLUMN_PROBABILITY))
				item.setProbability(object.getInt("value"));
		}
		
		return item;
	}

	
}
