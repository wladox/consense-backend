package com.consense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consense.model.User;
import com.consense.model.UserFeature;
import com.consense.repository.UserRepository;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Integer authenticateUser(User user) {

		User existingUser = userRepository.findUserByEmail(user.getEmail());
		if (user.getPassword().equals(existingUser.getPassword())) {
			return existingUser.getUserId();
		}
		return null;
	}

	@Override
	public User getUser(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public void addUser(User user) {
		userRepository.addUser(user);

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

}
