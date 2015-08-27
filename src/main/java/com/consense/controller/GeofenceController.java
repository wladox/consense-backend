package com.consense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consense.model.Geofence;
import com.consense.model.User;
import com.consense.service.GeofenceManagementService;

@RestController("geofenceController")
@RequestMapping("/geofence")
public class GeofenceController {
	
	private GeofenceManagementService geofenceManagementService;

	@Autowired
	public void setGeofenceManagementService(GeofenceManagementService geofenceManagementService) {
		this.geofenceManagementService = geofenceManagementService;
	}
	
	
	@RequestMapping(value = "/neighbours", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Geofence> findClosestNeighbour(
			@RequestParam(value = "lat") Double lat, 
			@RequestParam(value="long") Double longg) {
		
		return geofenceManagementService.findClosestGeofences(lat, longg);
	}
	
	@RequestMapping(value = "/{geofenceId}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> findAllUsersInGeofence(@PathVariable(value = "geofenceId") Integer geofenceId) {
		return geofenceManagementService.findAllUsersInGeofence(geofenceId);
	}

}
