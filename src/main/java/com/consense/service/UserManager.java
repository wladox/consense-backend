package com.consense.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consense.model.User;
import com.consense.model.UserFeature;
import com.consense.repository.UserRepository;

@Service
public class UserManager implements IUserManager {

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User authenticateUser(User user) {

		User existingUser = userRepository.findUserByEmail(user.getEmail());
		if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {
			return existingUser;
		}
		return null;
	}

	@Override
	public User getUser(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public int addUser(User user) {
		return userRepository.addUser(user);

	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<UserFeature> getUserFeatures(Integer userId) {
		List<UserFeature> userFeatures = userRepository.findFeaturesOfUser(userId);
		return userFeatures;
	}

	@Override
	public void addUserFeature(Integer userId, UserFeature feature) {
		userRepository.addUserFeature(userId, feature);
	}

	@Override
	public String setUserImage(Integer userId, String filepath) {
		String result = userRepository.setUserImage(userId, filepath);
		return result;
	}

	@Override
	public File getUserImage(Integer userId) {
		User user = userRepository.findUserById(userId);
		if (user.getImage() != null && !user.getImage().equals(""))
			return new File(user.getImage());
		return null;
	}

	@Override
	public List<User> findUsersInProximity(Integer userId) {
		return userRepository.getUsersInGeofence(userId);
	}

}
