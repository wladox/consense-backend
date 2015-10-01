package com.consense.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.consense.access.ConsensePermission;
import com.consense.access.ConsenseRole;
import com.consense.access.UserAssignment;
import com.consense.model.SocialRelationship;
import com.consense.model.User;

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
		
		String sql = "SELECT * FROM network.user_access_rules WHERE user_id = ?";
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<HashMap<Integer, Integer>>(){

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
			
		}, userId);
	}

	@Override
	public Integer addRole(ConsenseRole role) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		
		final String insertRole = "INSERT INTO network.role (name, enabled, activated) VALUES (null, 'true', 'true')";
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

	            PreparedStatement ps = connection.prepareStatement(insertRole, Statement.RETURN_GENERATED_KEYS);
	            return ps;
	        }
	    }, keyHolder);
		
		
		Integer id = (Integer) keyHolder.getKeys().get("role_id");
		System.out.println(id);
		String addPermissions = "INSERT INTO network.permission_assignment VALUES(?,?);";
		for (ConsensePermission p : role.getPermissions()) {
			jdbcTemplate.update(addPermissions, id, p.getPermId());
		}
		
		return id;
		
	}

	@Override
	public void addUserAssignment(Integer userId, Integer related_with, Integer roleId) {

		String sql ="INSERT INTO network.user_assignment VALUES(?, ?, now(), ?)";
		jdbcTemplate.update(sql, userId, related_with, roleId);
		
	}
	
	@Override
	public void updateUserAssignment(Integer userId, Integer related_with, Integer roleId) {

		String sql ="UPDATE network.user_assignment SET role_id = ? WHERE user_id = ? and related_with = ?";
		jdbcTemplate.update(sql, roleId, userId, related_with);
	}

	@Override
	public UserAssignment getUserAssignment(Integer userId, Integer related_with) {

		String sql = "SELECT user_id, related_with, created, role_id FROM network.user_assignment WHERE user_id = ? and related_with = ?";
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<UserAssignment>() {

			@Override
			public UserAssignment extractData(ResultSet rs) throws SQLException, DataAccessException{

				if (rs.next()) {
					UserAssignment assignment = new UserAssignment();
					assignment.setUserId(rs.getInt(1));
					assignment.setRelatedWith(rs.getInt(2));
					assignment.setCreated(rs.getDate(3));
					assignment.setRoleId(rs.getInt(4));
					return assignment;
				}
				
				return null;
				
			}
			
		}, userId, related_with); 
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

	@Override
	public void setAccessRuleOfUser(Integer userId, Integer categoryId, Integer lvl) {

		String sql = "UPDATE network.user_access_rules SET access_level = ? WHERE user_id = ? AND category_id = ?";
		jdbcTemplate.update(sql, lvl, userId, categoryId);
		
	}

	@Override
	public ConsenseRole getRole(Integer roleId) {
		
		String sql = "SELECT role_id, name, enabled, activated FROM network.role WHERE role_id = ?";
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<ConsenseRole>(){

			@Override
			public ConsenseRole extractData(ResultSet rs) throws SQLException, DataAccessException {

				if (rs.next()) {
					ConsenseRole role = new ConsenseRole();
					role.setRoleId(rs.getInt("role_id"));
					role.setName(rs.getString("name"));
					role.setEnabled(rs.getBoolean("enabled"));
					role.setActivated(rs.getBoolean("activated"));
					return role;
				}
				
				return null;
			}
			
		}, roleId);
	}

	@Override
	public void revokeRolePermissions(Integer roleId) {

		String sql = "DELETE from network.permission_assignment WHERE role_id = ?";
		jdbcTemplate.update(sql, roleId);
		
	}

	@Override
	public void updatePermissionAssignment(ConsenseRole role) {

		String sql = "INSERT INTO network.permission_assignment VALUES(?,?);";
		for (ConsensePermission p : role.getPermissions()) {
			jdbcTemplate.update(sql, role.getRoleId(), p.getPermId());
		}
		
	}

	@Override
	public Integer updateRole(ConsenseRole role) {
		
		int affectedRows = 0;
		String sql = "INSERT INTO network.permission_assignment VALUES(?,?);";
		for (ConsensePermission p : role.getPermissions()) {
			affectedRows += jdbcTemplate.update(sql, role.getRoleId(), p.getPermId());
		}
		
		return affectedRows;
	}

	@Override
	public List<Integer> getRoleIdsWithPermission(Integer permId) {

		String sql = "SELECT role_id FROM network.permission_assignment WHERE permission_id = ?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Integer>>(){

			@Override
			public List<Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<Integer> roleIds = new ArrayList<>();
				while(rs.next()) {
					roleIds.add(rs.getInt("role_id"));
				}
				
				return roleIds;
			}
			
		}, permId);
	}

	@Override
	public void deactivateRoles(List<Integer> roleIds) {

		String sql = "UPDATE network.role SET activated = 'false' WHERE role_id IN ("+ StringUtils.join(roleIds, ",") + ")";
		jdbcTemplate.update(sql);
		
	}

	@Override
	public List<Integer> getConcernedRoleIds(Integer userId) {
		
		String sql = "SELECT role_id FROM network.user_assignment WHERE related_with = ?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Integer>>(){

			@Override
			public List<Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<Integer> roleIds = new ArrayList<>();
				while(rs.next()) {
					roleIds.add(rs.getInt("role_id"));
				}
				
				return roleIds;
			}
			
		}, userId);
		
	}

	@Override
	public void disableRoles(List<Integer> roleIds) {
		String sql = "UPDATE network.role SET enabled = 'false' WHERE role_id IN ("+ StringUtils.join(roleIds, ",") + ")";
		jdbcTemplate.update(sql);
		
	}

	@Override
	public List<UserAssignment> getUserAssignments(Integer userId) {

		String sql = "SELECT * FROM network.user_assignment WHERE user_id = ? OR related_with = ?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<UserAssignment>>(){

			@Override
			public List<UserAssignment> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<UserAssignment> assignments = new ArrayList<>();
				while(rs.next()) {
					UserAssignment assignment = new UserAssignment();
					assignment.setUserId(rs.getInt("user_id"));
					assignment.setRelatedWith(rs.getInt("related_with"));
					assignment.setCreated(rs.getDate("created"));
					assignment.setRoleId(rs.getInt("role_id"));
					assignments.add(assignment);
				}
				
				return assignments;
			}
			
		}, userId, userId);
	}

	@Override
	public void enableRoles(List<Integer> roleIds) {
		String sql = "UPDATE network.role SET enabled = true WHERE role_id IN("+StringUtils.join(roleIds, ",")+");";
		jdbcTemplate.update(sql);
		
	}

	@Override
	public void activateRoles(List<Integer> roleIds) {
		String sql = "UPDATE network.role SET activated = true WHERE role_id IN("+StringUtils.join(roleIds, ",")+");";
		jdbcTemplate.update(sql);
	}

}
