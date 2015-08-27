package com.consense.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consense.model.Geofence;
import com.consense.model.User;
import com.consense.repository.GeofenceRepository;

@Service
public class GeofenceManagementServiceImpl implements GeofenceManagementService {

	private GeofenceRepository geofenceRepository;
	
	@Autowired
	public void setGeofenceRepository(GeofenceRepository geofenceRepository) {
		this.geofenceRepository = geofenceRepository;
	}

	@Override
	public void addGeofence(Geofence geofence) {
		geofenceRepository.addGeofence(geofence);

	}

	@Override
	public List<Geofence> findClosestGeofences(double lat, double longg) {
		return geofenceRepository.findClosestNeighbours(lat, longg);
	}

	@Override
	public List<User> findAllUsersInGeofence(Integer geofenceId) {
		List<User> userList = geofenceRepository.findAllUsersInGeofence(geofenceId);
		if (userList != null && userList.size() != 0) {
			return userList;
		}
		return new ArrayList<User>();
	}

}
