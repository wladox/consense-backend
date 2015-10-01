package com.consense.access;

import java.util.List;

public class ConsenseRole {

	public enum NetworkRole {
		DEFAULT(0), ATTENDEE(1);
		
		private int value;
		
		private NetworkRole(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		
	}
	
	private Integer roleId;
	private String 	name;
	private boolean enabled;
	private boolean activated;
	private List<ConsensePermission> permissions;
	
	public ConsenseRole() {}
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public List<ConsensePermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<ConsensePermission> permissions) {
		this.permissions = permissions;
	}
	
	
}
