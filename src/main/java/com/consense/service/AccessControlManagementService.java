package com.consense.service;

import java.util.List;

import com.consense.access.ConsensePermission;
import com.consense.access.ConsenseRole;

public interface AccessControlManagementService {

	void assignRole2User(Integer roleId, Integer userId);
	void assignPermission2Role(Integer roleId, Integer permissionId);
	boolean checkUserAssignment(Integer userId, Integer roleId);
	void addRole(ConsenseRole role);
	List<ConsensePermission> getRolePermissions(Integer roleId);
	ConsensePermission getPermission(Integer permissionId);
	boolean roleContainsPermission(Integer roleId, Integer permissionId);
	void removeUserAssignment(Integer userId, Integer roleId);
}
