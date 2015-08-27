package com.consense.service;

import java.util.List;

import com.consense.model.User;
import com.consense.model.UserFeature;

public interface UserManagementService {

	Integer authenticateUser(User user);
	User getUser(String email);
	void addUser(User user);
	List<User> getAllUsers();
	List<UserFeature> getUserFeatures(Integer userId);
	void addUserFeature(Integer userId, UserFeature feature);
	
}