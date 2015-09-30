package com.consense.repository;

import java.util.HashMap;
import java.util.List;

import com.consense.access.ConsensePermission;
import com.consense.access.ConsenseRole;
import com.consense.model.SocialRelationship;

public interface ACRepository {

	/**
	 * 
	 * @param userId of the requested profile's owner
	 * @return Hashmap containing category ids as key and access level as value
	 */
	HashMap<Integer, Integer> getAccessRulesOfUser(Integer userId);
	void setAccessRuleOfUser(Integer userId, Integer categoryId, Integer lvl);
	
	void addUserAssignment(Integer userId, Integer roleId);
	List<Integer> getUserAssignments(Integer userId);
	void addRole(ConsenseRole role);
	
	void createSocialRelationship(SocialRelationship sr);
	SocialRelationship findSocialRelationship(Integer user1id, Integer user2Id);
	void updateSocialRelationship(Integer socRelId, Integer user1Id, Integer user2Id, Integer roleId);
	
	List<ConsensePermission> getPermissionsOfCategories(List<Integer> categoryIds);
	ConsensePermission getPermissionOfCategory(Integer categoryId);
	
	
	
	
}
