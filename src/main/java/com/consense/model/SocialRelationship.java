package com.consense.model;

public class SocialRelationship {

	private int socialRelationshipId;
	private Integer user1Id;
	private Integer user2Id;
	private Integer role1Id;
	private Integer role2Id;
	
	public SocialRelationship() {
		
	}

	public int getSocialRelationshipId() {
		return socialRelationshipId;
	}

	public void setSocialRelationshipId(int socialRelationshipId) {
		this.socialRelationshipId = socialRelationshipId;
	}

	public Integer getUser1Id() {
		return user1Id;
	}

	public void setUser1Id(Integer user1Id) {
		this.user1Id = user1Id;
	}

	public Integer getUser2Id() {
		return user2Id;
	}

	public void setUser2Id(Integer user2Id) {
		this.user2Id = user2Id;
	}

	public Integer getRole1Id() {
		return role1Id;
	}

	public void setRole1Id(Integer role1Id) {
		this.role1Id = role1Id;
	}

	public Integer getRole2Id() {
		return role2Id;
	}

	public void setRole2Id(Integer role2Id) {
		this.role2Id = role2Id;
	}
	
	
	
}
