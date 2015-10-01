package com.consense.model.context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

public class MusicItem extends ContextItem {

	public static final String TABLE_NAME = "state_music";
	
	public static final String COLUMN_ALBUM = "album";
	public static final String COLUMN_AUTHOR = "author";
	public static final String COLUMN_TITLE = "title";
	
	private String album;
	private String author;
	private String title;
	
	public MusicItem() {
		super();
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static MusicItem getFromResult(ResultSet rs) {
		MusicItem item = new MusicItem();
		try {
//			item.setItemId(rs.getInt("id"));
			item.setType(rs.getInt("type"));
			item.setCreated(rs.getTimestamp("created"));
			item.setAlbum(rs.getString(COLUMN_ALBUM));
			item.setAuthor(rs.getString(COLUMN_AUTHOR));
			item.setTitle(rs.getString(COLUMN_TITLE));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public static MusicItem getFromJSON(JSONObject json) {
		MusicItem item = new MusicItem();
		//item.setItemId(json.getInt("context_state_id"));
		item.setType(ContextItem.TYPE_MUSIC);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(json.getLong("created"));
		item.setCreated(c.getTime());
		JSONArray params = json.getJSONArray("params");
		for (int i = 0; i < params.length(); i++) {
			JSONObject object = params.getJSONObject(i);
			if (object.getString("name").equals(MusicItem.COLUMN_ALBUM)) 
				item.setAlbum(object.getString("value"));
			else if (object.getString("name").equals(MusicItem.COLUMN_AUTHOR))
				item.setAuthor(object.getString("value"));
			else if (object.getString("name").equals(MusicItem.COLUMN_TITLE))
				item.setTitle(object.getString("value"));
		}
		
		return item;
	}
	
	

}
