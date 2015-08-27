package com.consense.repository;

import java.util.List;

import com.consense.model.Geofence;
import com.consense.model.User;

public interface GeofenceRepository {

	Geofence getGeofence();
	void addGeofence(Geofence geofence);
	List<Geofence> findClosestNeighbours(double lat, double longg);
	boolean isUserInGeofence(Integer userId, Integer geofenceId);
	List<User> findAllUsersInGeofence(Integer geofenceId);
	
}
