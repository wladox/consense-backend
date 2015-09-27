package com.consense.service;

import java.util.List;

import com.consense.model.Geofence;
import com.consense.model.User;

public interface IGeofenceManager {

	void addGeofence(Geofence geofence);
	List<Geofence> findClosestGeofences(double lat, double longg);
	List<User> findAllUsersInGeofence(Integer geofenceId);
}
