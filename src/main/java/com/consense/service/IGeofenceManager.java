package com.consense.service;

import java.util.List;

import com.consense.model.Geofence;
import com.consense.model.User;

public interface IGeofenceManager {

	void addGeofence(Geofence geofence);
	List<Geofence> findClosestGeofences(double lat, double longg);
	List<User> findAllUsersInGeofence(Integer geofenceId);
	int addUser2Geofence(Integer userId, Integer geofenceId);
	int removeUserFromGeofence(Integer userId, Integer geofenceId);
}
