package com.consense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consense.model.User;
import com.consense.model.UserFeature;
import com.consense.service.AccessControlManagementService;
import com.consense.service.UserManagementService;


@RestController("userController")
@RequestMapping("/user")
public class UserController {

	private UserManagementService userManager;
	private AccessControlManagementService accessControlManager;

	
	// wiring required components
	
	@Autowired
	public void setUserManager(UserManagementService userManager) {
		this.userManager = userManager;
	}
	
	@Autowired
	public void setAccessControlManager(AccessControlManagementService accessControlManager) {
		this.accessControlManager = accessControlManager;
	}

	
	//request mappings
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<User> findAll() {
		return userManager.getAllUsers();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	ResponseEntity<?> addUser(@RequestBody User user) {
		userManager.addUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	ResponseEntity<List<UserFeature>> login(@RequestBody User user) {
		
		Integer authenticatedUser = userManager.authenticateUser(user);
		
		if (authenticatedUser != null) {
			List<UserFeature> features = userManager.getUserFeatures(authenticatedUser);
			ResponseEntity<List<UserFeature>> response = new ResponseEntity<List<UserFeature>>(features, HttpStatus.OK);
			return response;
		}
		else
			return new ResponseEntity<List<UserFeature>>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/feature/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) 
	public void addUserFeature(	@RequestParam(value = "userId") Integer userId, 
								@RequestParam(value="featureName") String featureName,
								@RequestParam(value="categoryId") Integer catId) {
		UserFeature feature = new UserFeature();
		feature.setFeatureName(featureName);
		feature.setCategoryId(catId);
		userManager.addUserFeature(userId, feature);
	}
	
	@RequestMapping(value="/{userId}/profile", method = RequestMethod.POST)
	public List<UserFeature> getUserProfile(@PathVariable(value="userId") Integer userId) { 
		
		return null;
	}
}
