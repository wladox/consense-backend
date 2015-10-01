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
import com.consense.access.UserAssignment;
import com.consense.model.SocialRelationship;
import com.consense.model.UserFeature;
import com.consense.model.context.AppsItem;
import com.consense.model.context.ContextItem;
import com.consense.model.context.ContextItemFilter;
import com.consense.model.context.MusicItem;
import com.consense.repository.ACRepository;

@Service
public class AccessControlManager implements IAccessControlManager {
	
	private IContextManager contextManager;
	private ACRepository 	accessControlRepository;
	private IUserManager 	userManager;
	
	@Autowired
	public void setContextManager(IContextManager contextManager) {
		this.contextManager = contextManager;
	}
	
	@Autowired
	public void setAccessControlRepository(ACRepository accessControlRepository) {
		this.accessControlRepository = accessControlRepository;
	}
	
	@Autowired
	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
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
	public Integer addRole(ConsenseRole role) {
		Integer roleId = accessControlRepository.addRole(role);
		return roleId;
		
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
	public List<UserFeature> getUserProfile(Integer requesterId, Integer ownerId) {

		// social relationship between requester and profile owner
		//SocialRelationship sr = findSocialRelationship(requesterId, ownerId);
		// new role for the requester
		
		// permission for new role for requester user in this social relationship
		
		UserAssignment assignment = accessControlRepository.getUserAssignment(requesterId, ownerId);
		
		if (assignment != null) {
			
			if (assignment.getRoleId().intValue() != 0) {
				ConsenseRole role = accessControlRepository.getRole(assignment.getRoleId());
				
				if (role.isEnabled()) {
					if (!role.isActivated()) { 
						accessControlRepository.revokeRolePermissions(role.getRoleId());
						List<ConsensePermission> newPermissions = checkAccessRules(requesterId, ownerId);
						role.setPermissions(newPermissions);
						Integer rows = accessControlRepository.updateRole(role);
						if (rows == newPermissions.size()) {
							List<Integer> roleIds = new ArrayList<>();
							roleIds.add(role.getRoleId());
							accessControlRepository.activateRoles(roleIds);
						}
					}
				} else {
					return null; // the profile owner has probably leaved the geofence
				}
					
			} else {
				Integer roleId = addNewRole(requesterId, ownerId);
				accessControlRepository.updateUserAssignment(requesterId, ownerId, roleId);
			}
			
		} else {
			Integer roleId = addNewRole(requesterId, ownerId);
			accessControlRepository.addUserAssignment(requesterId, ownerId, roleId);
		}
		
		return userManager.getFeaturesOfUser(requesterId, ownerId);
	}

	private Integer addNewRole(Integer requesterId, Integer ownerId) {
		List<ConsensePermission> newPermissions = checkAccessRules(requesterId, ownerId);
		ConsenseRole newRole = new ConsenseRole();
		newRole.setPermissions(newPermissions);
		Integer roleId = accessControlRepository.addRole(newRole);
		return roleId;
	}

	private List<ConsensePermission> checkAccessRules(Integer requesterId, Integer ownerId) {
		
		ContextItemFilter filter = new ContextItemFilter();
		Calendar c = Calendar.getInstance();
		
		filter.setTo(c.getTime());
		c.add(Calendar.DAY_OF_YEAR, -7);
		filter.setFrom(c.getTime());
		
		List<ConsensePermission> newPermissions = new ArrayList<>();
		HashMap<Integer, Integer> rules = accessControlRepository.getAccessRulesOfUser(ownerId);
		
		for (Integer categoryId : rules.keySet()) {
			switch(rules.get(categoryId)) {
			case 0: // do nothing and just add the permission to the role
				newPermissions.add(accessControlRepository.getPermissionOfCategory(categoryId));
				break;
			case 1: // check context
				ConsensePermission permission 		= accessControlRepository.getPermissionOfCategory(categoryId);
				
				if (permission.getName().equals(PermissionName.APPS.toString().toLowerCase())) {
					filter.setType(ContextItem.TYPE_APPS);
					
					@SuppressWarnings("unchecked")
					List<AppsItem> appsItemsOfRequester = (List<AppsItem>) contextManager.getContextItems(requesterId, filter);
					@SuppressWarnings("unchecked")
					List<AppsItem> appsItemsOfOwner = (List<AppsItem>) contextManager.getContextItems(ownerId, filter);
					
					String[] requesterApps = appsItemsOfRequester.get(0).getNames().split(",");
					String[] ownerApps = appsItemsOfOwner.get(0).getNames().split(",");

					int matches = 0;
					
					for(int i = 0; i < requesterApps.length; i++) {
						for (int j = 0; j < ownerApps.length; j++) {
							if (requesterApps[i].equals(ownerApps[j])) {
								matches++;
								break;
							}
						}
					}
					
					if (matches > 5) 
						newPermissions.add(permission);
					
				} else if (permission.getName().equals(PermissionName.ACTIVITY.toString().toLowerCase())) {
					//filter.setType(ContextItem.TYPE_ACTIVITY);
				} else if (permission.getName().equals(PermissionName.MUSIC.toString().toLowerCase())) {
					filter.setType(ContextItem.TYPE_MUSIC);
					
					@SuppressWarnings("unchecked")
					List<MusicItem> musicItemsOfRequester 	= (List<MusicItem>) contextManager.getContextItems(requesterId, filter);
					@SuppressWarnings("unchecked")
					List<MusicItem> musicItemsOfOwner 		= (List<MusicItem>) contextManager.getContextItems(ownerId, filter);
					
					int matches = 0;
					
					for (MusicItem requesterItem : musicItemsOfRequester) {
						for (MusicItem ownerItem : musicItemsOfOwner) {
							if (ownerItem.getAlbum().equals(requesterItem.getAlbum())) {
								matches++;
							} else if (ownerItem.getAuthor().equals(requesterItem.getAuthor()) && ownerItem.getTitle().equals(requesterItem.getTitle())) {
								matches++;
								break;
							}
						}
					}
					
					if (matches > 5)
						newPermissions.add(permission);
				}
				break;
			case 2: // do nothing
				break;
			}
		}
		return newPermissions;
	}

	@Override
	public void setUserAccessLevel(Integer userId, Integer categoryId, Integer lvl) {
		accessControlRepository.setAccessRuleOfUser(userId, categoryId, lvl);

		// find roles which potentially might be re-checked 
		List<Integer> roleIds = accessControlRepository.getConcernedRoleIds(userId);
		
		if (roleIds.size() > 0)
			accessControlRepository.deactivateRoles(roleIds);
	}

	@Override
	public void disableRoles(List<Integer> roleIds) {
		accessControlRepository.disableRoles(roleIds);
	}

	@Override
	public List<UserAssignment> getUserAssignments(Integer userId) {
		return accessControlRepository.getUserAssignments(userId);
	}

	@Override
	public void enableRoles(List<Integer> roleIds) {
		accessControlRepository.enableRoles(roleIds);
		
	}

	
}
