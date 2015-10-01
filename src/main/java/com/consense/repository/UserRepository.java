package com.consense.repository;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.consense.model.User;
import com.consense.model.UserFeature;

@PreAuthorize("hasRole('ROLE_USER')")
public interface UserRepository {

	List<User> findAll();
	User findUserById(Integer id);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	int addUser(User user);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	User findUserByName(String name);
	User findUserByEmail(String email);
	
	List<UserFeature> getOwnFeatures(Integer userId);
	List<UserFeature> getFeaturesOfUser(Integer requesterId, Integer ownerId);
	
	void updateUserFeature(UserFeature feature);
	void addUserFeature(Integer userId, UserFeature feature);
	String setUserImage(Integer userId, String filepath);
	
	List<User> getUsersInGeofence(Integer userId);
}
