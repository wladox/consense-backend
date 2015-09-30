package com.consense.service;

import java.util.List;

import com.consense.access.ConsensePermission;
import com.consense.access.ConsenseRole;
import com.consense.model.SocialRelationship;
import com.consense.model.UserFeature;

public interface IAccessControlManager {

	void assignRole2User(Integer roleId, Integer userId);
	void assignPermission2Role(Integer roleId, Integer permissionId);
	boolean checkUserAssignment(Integer userId, Integer roleId);
	void addRole(ConsenseRole role);
	List<ConsensePermission> getRolePermissions(Integer roleId);
	ConsensePermission getPermission(Integer permissionId);
	boolean roleContainsPermission(Integer roleId, Integer permissionId);
	void removeUserAssignment(Integer userId, Integer roleId);
	void setUserAccessLevel(Integer userId, Integer categoryId, Integer lvl);
	SocialRelationship findSocialRelationship(Integer user1Id, Integer user2Id); 
	
	List<UserFeature> getUserProfile(Integer requesterId, Integer userId);
}
