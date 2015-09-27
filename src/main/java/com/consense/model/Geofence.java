package com.consense.model;

import java.util.Date;

public class Geofence {
	
	public static final String SCHEMA = "geo";
	public static final String TABLE_NAME = "geofence";
	
	public static final String COLUMN_ID 		= "geofence_id";
	public static final String COLUMN_NAME 		= "name";
	public static final String COLUMN_RADIUS 	= "radius";
	public static final String COLUMN_DURATION 	= "duration";
	public static final String COLUMN_CREATED 	= "created";
	public static final String COLUMN_LAT 		= "lat";
	public static final String COLUMN_LONG 		= "long";

	private Integer geofenceId;
	private String 	name;
	
	private double 	latitude;
	private double 	longitude;
	private Integer radius;
	
	private long 	duration; // in miliseconds
	private Date 	created;
	
	public Geofence() {}
	
	public Integer getGeofenceId() {
		return geofenceId;
	}
	public void setGeofenceId(Integer geofenceId) {
		this.geofenceId = geofenceId;
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
	public Integer getRadius() {
		return radius;
	}
	public void setRadius(Integer radius) {
		this.radius = radius;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
