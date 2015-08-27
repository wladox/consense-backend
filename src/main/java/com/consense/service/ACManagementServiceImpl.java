package com.consense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consense.access.ConsensePermission;
import com.consense.access.ConsenseRole;
import com.consense.repository.ACRepository;

@Service
public class ACManagementServiceImpl implements AccessControlManagementService {
	
	private ContextManagementService contextManager;
	private ACRepository accessControlRepository;
	
	@Autowired
	public void setContextManager(ContextManagementService contextManager) {
		this.contextManager = contextManager;
	}
	
	@Autowired
	public void setAccessControlRepository(ACRepository accessControlRepository) {
		this.accessControlRepository = accessControlRepository;
	}

	@Override
	public void assignRole2User(Integer roleId, Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assignPermission2Role(Integer roleId, Integer permissionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkUserAssignment(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addRole(ConsenseRole role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ConsensePermission> getRolePermissions(Integer roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConsensePermission getPermission(Integer permissionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean roleContainsPermission(Integer roleId, Integer permissionId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeUserAssignment(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		
	}

	
}
