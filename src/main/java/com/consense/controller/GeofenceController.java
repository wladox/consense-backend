package com.consense.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consense.access.UserAssignment;
import com.consense.model.Geofence;
import com.consense.model.User;
import com.consense.service.IAccessControlManager;
import com.consense.service.IGeofenceManager;
import com.consense.service.IUserManager;

@RestController("geofenceController")
@RequestMapping("/geofence")
public class GeofenceController {
	
	private IGeofenceManager 		geofenceManagementService;
	private IUserManager			userManager;
	private IAccessControlManager 	accessControlManager;

	@Autowired
	public void setGeofenceManagementService(IGeofenceManager geofenceManagementService) {
		this.geofenceManagementService = geofenceManagementService;
	}
	
	@Autowired
	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}
	
	@Autowired
	public void setAccessControlManager(IAccessControlManager accessControlManager) {
		this.accessControlManager = accessControlManager;
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
	
	@RequestMapping(value="/adduser", method = RequestMethod.POST)
	public ResponseEntity<Void> add2Geofence(
			@RequestParam(value="userId") Integer userId, 
			@RequestParam(value="geofences") String geofences) {
	
		String[] geofenceIds = geofences.split(",");
		int rows = 0;
		for (int i = 0; i < geofenceIds.length; i++) {
			rows += geofenceManagementService.addUser2Geofence(userId, Integer.valueOf(geofenceIds[i]));
		}

		if (rows == geofenceIds.length) {
			List<UserAssignment> userAssignments = accessControlManager.getUserAssignments(userId);
			List<Integer> roleIds = new ArrayList<>();
			for (UserAssignment ua : userAssignments) {
				roleIds.add(ua.getRoleId());
				accessControlManager.enableRoles(roleIds);
			}
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
			
		return new ResponseEntity<Void>(HttpStatus.NOT_MODIFIED);
	}
	
	@RequestMapping(value="/removeuser", method = RequestMethod.POST)
	public ResponseEntity<Void> removeFromGeofence(
			@RequestParam(value="userId") Integer userId, 
			@RequestParam(value="geofences") String geofences) {
	
		String[] geofenceIds = geofences.split(",");
		int rows = 0;
		for (int i = 0; i < geofenceIds.length; i++) {
			geofenceManagementService.removeUserFromGeofence(userId, Integer.valueOf(geofenceIds[i]));
		}

		if (rows == geofenceIds.length) {
			List<UserAssignment> userAssignments = accessControlManager.getUserAssignments(userId);
			List<Integer> roleIds = new ArrayList<>();
			for (UserAssignment ua : userAssignments) {
				roleIds.add(ua.getRoleId());
				accessControlManager.disableRoles(roleIds);
			}
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_MODIFIED);
	}
	
	@RequestMapping(value="/users" , method = RequestMethod.POST)
	public List<User> getUsers(@RequestParam(value="userId") Integer userId) {
		List<User> users = userManager.findUsersInProximity(userId);
		return users;
	}

}
