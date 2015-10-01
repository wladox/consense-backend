package com.consense.repository;

import java.util.HashMap;
import java.util.List;

import com.consense.access.ConsensePermission;
import com.consense.access.ConsenseRole;
import com.consense.access.UserAssignment;
import com.consense.model.SocialRelationship;
import com.consense.model.User;

public interface ACRepository {

	/**
	 * 
	 * @param userId of the requested profile's owner
	 * @return Hashmap containing category ids as key and access level as value
	 */
	HashMap<Integer, Integer> getAccessRulesOfUser(Integer userId);
	void setAccessRuleOfUser(Integer userId, Integer categoryId, Integer lvl);
	
	void addUserAssignment(Integer userId, Integer related_with, Integer roleId);
	UserAssignment getUserAssignment(Integer requesterId, Integer ownerId);
	void updateUserAssignment(Integer userId, Integer related_with, Integer roleId);
	
	Integer addRole(ConsenseRole role);
	ConsenseRole getRole(Integer roleId);
	Integer updateRole(ConsenseRole role);
	
	void createSocialRelationship(SocialRelationship sr);
	SocialRelationship findSocialRelationship(Integer user1id, Integer user2Id);
	void updateSocialRelationship(Integer socRelId, Integer user1Id, Integer user2Id, Integer roleId);
	
	List<ConsensePermission> getPermissionsOfCategories(List<Integer> categoryIds);
	ConsensePermission getPermissionOfCategory(Integer categoryId);
	void revokeRolePermissions(Integer roleId);
	
	void updatePermissionAssignment(ConsenseRole role);
	List<Integer> getRoleIdsWithPermission(Integer permId);
	List<Integer> getConcernedRoleIds(Integer userId);
	List<UserAssignment> getUserAssignments(Integer userId);
	
	void enableRoles(List<Integer> roleIds);
	void deactivateRoles(List<Integer> roleIds);
	void disableRoles(List<Integer> roleIds);
	void activateRoles(List<Integer> roleIds);
	
	
	
	
}
