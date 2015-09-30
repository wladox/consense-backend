package com.consense.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consense.access.ConsensePermission;
import com.consense.access.ConsensePermission.PermissionName;
import com.consense.access.ConsenseRole;
import com.consense.model.SocialRelationship;
import com.consense.model.UserFeature;
import com.consense.model.context.ContextItem;
import com.consense.model.context.ContextItemFilter;
import com.consense.repository.ACRepository;

@Service
public class AccessControlManager implements IAccessControlManager {
	
	private IContextManager contextManager;
	private ACRepository accessControlRepository;
	
	@Autowired
	public void setContextManager(IContextManager contextManager) {
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

	@Override
	public SocialRelationship findSocialRelationship(Integer user1Id, Integer user2Id) {

		SocialRelationship sr = accessControlRepository.findSocialRelationship(user1Id, user2Id);
		if (sr != null)
			return sr;
		return null;
	}

	@Override
	public List<UserFeature> getUserProfile(Integer requesterId, Integer userId) {

		ContextItemFilter filter = new ContextItemFilter();
		Calendar c = Calendar.getInstance();
		
		filter.setTo(c.getTime());
		c.add(Calendar.DAY_OF_YEAR, -7);
		filter.setFrom(c.getTime());
		
		SocialRelationship sr = findSocialRelationship(requesterId, userId);
		if (sr != null) {
			//Key: categoryId, Value: Access LvL
			HashMap<Integer, Integer> accRules = accessControlRepository.getAccessRulesOfUser(userId);
			ConsenseRole newRole = new ConsenseRole();
			List<ConsensePermission> permissionsList = new ArrayList<>();
			
			for (Integer categoryId : accRules.keySet()) {
				switch(accRules.get(categoryId)) {
				case 0:
					permissionsList.add(accessControlRepository.getPermissionOfCategory(categoryId));
					break;
				case 1:
					ConsensePermission permission 		= accessControlRepository.getPermissionOfCategory(categoryId);
//					ContextRequirement conRequirement 	= contextManager.getContextRequirement(permission.getPermId());
					
//					List<ContextItem> requesterContext 	= contextManager.get(requesterId);
					
					filter.setTo(c.getTime());
					c.add(Calendar.DAY_OF_YEAR, -7);
					filter.setFrom(c.getTime());
					
					List<ContextItem> userContext 		= contextManager.getContextItems(userId, filter);
					
					//Match context depending on context requirements
					
					
					break;
				case 2:
					break;
				}
			}
		} else {
			SocialRelationship newSR = new SocialRelationship();
			newSR.setUser1Id(requesterId);
			newSR.setUser2Id(userId);
			
			HashMap<Integer, Integer> rules = accessControlRepository.getAccessRulesOfUser(userId);
			List<ConsensePermission> newPermissions = new ArrayList<>();
			
			for (Integer categoryId : rules.keySet()) {
				switch(rules.get(categoryId)) {
				case 0:
					newPermissions.add(accessControlRepository.getPermissionOfCategory(categoryId));
					break;
				case 1:
					ConsensePermission permission 		= accessControlRepository.getPermissionOfCategory(categoryId);
					List<ContextItem> requesterContext = null;
					List<ContextItem> profileOwnerContext = null;
					if (permission.getName().equals(PermissionName.APPS.toString().toLowerCase())) {
						filter.setType(ContextItem.TYPE_APPS);
					} else if (permission.getName().equals(PermissionName.ACTIVITY.toString().toLowerCase())) {
						filter.setType(ContextItem.TYPE_ACTIVITY);
					} else if (permission.getName().equals(PermissionName.MUSIC.toString().toLowerCase())) {
						filter.setType(ContextItem.TYPE_MUSIC);
					}
					requesterContext 	= contextManager.getContextItems(requesterId, filter);
					profileOwnerContext = contextManager.getContextItems(userId, filter);
					
//					ContextRequirement conRequirement 	= contextManager.getContextRequirement(permission.getPermId());
					
					//Match context depending on context requirements
					
					
					break;
				case 2:
					break;
				}
			}
			// create social relationship
			// store it in DB
			// retrieve access rules of both users
			// find similarities in context
			// 
			
		}
		
		return null;
	}

	@Override
	public void setUserAccessLevel(Integer userId, Integer categoryId, Integer lvl) {
		accessControlRepository.setAccessRuleOfUser(userId, categoryId, lvl);
	}

	
}
