package com.consense.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.consense.access.ConsensePermission;
import com.consense.access.ConsenseRole;
import com.consense.model.SocialRelationship;

@Repository
public class JdbcACRepository implements ACRepository {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcACRepository() {}
	
	@Autowired
	public JdbcACRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public HashMap<Integer, Integer> getAccessRulesOfUser(Integer userId) {
		
		String sql = "SELECT * FROM network.user_access_rules WHERE user_id = userId";
		
		jdbcTemplate.query(sql, new ResultSetExtractor<HashMap<Integer, Integer>>(){

			@Override
			public HashMap<Integer, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {

				HashMap<Integer, Integer> accessRules = new HashMap<>();
				
				while(rs.next()) {
					Integer catId = rs.getInt("category_id");
					Integer accessLvl = rs.getInt("access_level");
					accessRules.put(catId, accessLvl);
				}
				
				return accessRules;
			}
			
		});
		return null;
	}

	@Override
	public void addRole(ConsenseRole role) {
		
		String sql = "INSERT INTO network.role VALUES (?) RETURNING role_id";
		int id = jdbcTemplate.update(sql, role.getRoleId());
		
		String sql2 = "INSERT INTO network.permission_assignment VALUES(?,?)";
		for (ConsensePermission p : role.getPermissions()) {
			jdbcTemplate.update(sql2, role.getRoleId(), p.getPermId());
		}
		
	}

	@Override
	public void addUserAssignment(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> getUserAssignments(Integer userId) {

		String sql = "SELECT role_id FROM network.user_assignment WHERE user_id = ?";
		
		jdbcTemplate.query(sql, new ResultSetExtractor<List<Integer>>() {

			@Override
			public List<Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<Integer> roleIds  = new ArrayList<>();
				
				while(rs.next()) {
					roleIds.add(rs.getInt("role_id"));
				}
				
				return roleIds;
			}
			
		}, userId); 
		
		return new ArrayList<Integer>();
	}

	@Override
	public void createSocialRelationship(SocialRelationship sr) {

		String sql = "INSERT (user1_id, user2_id, role1_id, role2_id) INTO network.social_relationship "
				+ "VALUES (?,?,?,?)";
		
		jdbcTemplate.update(sql, sr.getUser1Id(), sr.getUser2Id(), sr.getRole1Id(), sr.getRole2Id());
		
		
	}

	@Override
	public SocialRelationship findSocialRelationship(final Integer user1id, final Integer user2Id) {
		
		String sql = "SELECT * FROM network.social_relationship WHERE (user1_id = ? or user2_id = ?) and (user1_id = ? or user2_id = ?)";
		
		jdbcTemplate.query(sql, new ResultSetExtractor<SocialRelationship>(){

			@Override
			public SocialRelationship extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()) {
					SocialRelationship sr = new SocialRelationship();
					sr.setSocialRelationshipId(rs.getInt("sr_id"));
					sr.setUser1Id(rs.getInt("user1_id"));
					sr.setRole1Id(rs.getInt("role1_id"));
					sr.setUser2Id(rs.getInt("user2_id"));
					sr.setRole2Id(rs.getInt("role2_id"));
					return sr;
				}
				
				return null;
			}
			
		}, user1id, user2Id, user1id, user2Id);
		return null;
	}

	@Override
	public void updateSocialRelationship(Integer socRelId, Integer user1Id, Integer user2Id, Integer roleId) {
		
		SocialRelationship sr = findSocialRelationship(user1Id, user2Id);
		
		String sql = "UPDATE network.social_relationship SET ";
		
		// to implement
		
	}

	@Override
	public List<ConsensePermission> getPermissionsOfCategories(List<Integer> categoryIds) {

		final List<ConsensePermission> permissions = new ArrayList<>();
		
		for (Integer catId : categoryIds) {
			String sql = "SELECT permission_id, name, cat_id FROM network.permission WHERE cat_id = " + catId;
			ConsensePermission permission = jdbcTemplate.query(sql, new ResultSetExtractor<ConsensePermission>() {

				@Override
				public ConsensePermission extractData(ResultSet rs) throws SQLException, DataAccessException {

					if (rs.next()) {
						ConsensePermission cp = new ConsensePermission();
						cp.setPermId(rs.getInt("permission_id"));
						cp.setName(rs.getString("name"));
						cp.setCategoryId(rs.getInt("cat_id"));
						
						return cp;
					}
					
					return null;
				}
				
			});
			
			if (permission != null) 
				permissions.add(permission);
			
		}
		
		return permissions;
	}

	@Override
	public ConsensePermission getPermissionOfCategory(Integer categoryId) {

		String sql = "SELECT permission_id, name FROM network.permission WHERE cat_id = " + categoryId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<ConsensePermission>(){

			@Override
			public ConsensePermission extractData(ResultSet rs) throws SQLException, DataAccessException {

				if (rs.next()) {
					ConsensePermission cp = new ConsensePermission();
					cp.setPermId(rs.getInt("permission_id"));
					cp.setName(rs.getString("name"));
					return cp;
				}
				
				return null;
			}
			
		});
	}

}
