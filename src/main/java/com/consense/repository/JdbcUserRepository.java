package com.consense.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.consense.model.User;
import com.consense.model.UserFeature;

@Repository
public class JdbcUserRepository implements UserRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcUserRepository() {}
	
	@Autowired
	public JdbcUserRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<User> findAll() {

		String sql = "SELECT * FROM network.user";
	    List<User> listUser = jdbcTemplate.query(sql, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUserId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setBirthday(rs.getDate("birthday"));
				user.setSex(rs.getString("sex"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setImage(rs.getString("image"));
				return user;
			}
		});
	 
	    return listUser;
	}

	public User findUserById(Integer id) {
		String sql = "SELECT * FROM network.user WHERE id = " + id;
		return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

			@Override
			public User extractData(ResultSet rs) throws SQLException {
				if (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt(User.COLUMN_USER_ID));
					user.setUsername(rs.getString(User.COLUMN_USERNAME));
					user.setEmail(rs.getString(User.COLUMN_EMAIL));
					user.setPassword(rs.getString(User.COLUMN_PASSWORD));
					user.setName(rs.getString(User.COLUMN_NAME));
					user.setSurname(rs.getString(User.COLUMN_SURNAME));
					user.setBirthday(rs.getDate(User.COLUMN_BIRTHDAY));
					user.setSex(rs.getString(User.COLUMN_SEX));
					user.setImage(rs.getString("image"));
					return user;
				}
				return null;
			}
		});
	}

	public int addUser(User newUser) {

//		if (newUser.getUserId() > 0) {
//	        // update
//	        String sql = "UPDATE network.user SET "+ User.COLUMN_EMAIL +"=?, " + User.COLUMN_PASSWORD + "=? WHERE id=?";
//	        jdbcTemplate.update(sql, newUser.getEmail(), newUser.getPassword(), newUser.getUserId());
//	    } else {
	        // insert
	        String sql = "INSERT INTO network.user ("+User.COLUMN_USERNAME+", " + User.COLUMN_NAME + ", " + User.COLUMN_SURNAME + ", "+User.COLUMN_EMAIL+", "+User.COLUMN_PASSWORD+") VALUES (?, ?, ?, ?, ?)";
	        return jdbcTemplate.update(sql, newUser.getName(), newUser.getName(), newUser.getSurname(), newUser.getEmail(), newUser.getPassword());
//	    }
	}

	@Override
	public User findUserByName(String name) {
		String sql = "SELECT * FROM network.user WHERE username = " + name;
		return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

			@Override
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt(User.COLUMN_USER_ID));
					user.setUsername(rs.getString(User.COLUMN_USERNAME));
					user.setEmail(rs.getString(User.COLUMN_EMAIL));
					user.setPassword(rs.getString(User.COLUMN_PASSWORD));
					user.setName(rs.getString(User.COLUMN_NAME));
					user.setSurname(rs.getString(User.COLUMN_SURNAME));
					user.setBirthday(rs.getDate(User.COLUMN_BIRTHDAY));
					return user;
				}
				return null;
			}
		});
	}

	@Override
	public User findUserByEmail(String email) {
		String sql = "SELECT * FROM network.user WHERE "+User.COLUMN_EMAIL+" = '" + email + "'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

			@Override
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt(User.COLUMN_USER_ID));
					user.setUsername(rs.getString(User.COLUMN_USERNAME));
					user.setEmail(rs.getString(User.COLUMN_EMAIL));
					user.setPassword(rs.getString(User.COLUMN_PASSWORD));
					user.setName(rs.getString(User.COLUMN_NAME));
					user.setSurname(rs.getString(User.COLUMN_SURNAME));
					user.setBirthday(rs.getDate(User.COLUMN_BIRTHDAY));
					return user;
				}
				return null;
			}
		});
	}

	@Override
	public List<UserFeature> findFeaturesOfUser(Integer userId) {
		
		String sql = "SELECT DISTINCT f.id, f.name, f.cat_id, fc.name AS cat_name FROM network.feature f "
				+ "JOIN network.user_feature uf on f.id = uf.feature_id "
				+ "JOIN network.feature_category fc on f.cat_id = fc.id "
				+ "JOIN network.user on uf.user_id = " + userId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<UserFeature>>() {

			@Override
			public List<UserFeature> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<UserFeature> features = new ArrayList<>();
				while (rs.next()) {
					UserFeature userFeature = new UserFeature();
					userFeature.setFeatureId(rs.getInt(UserFeature.COLUMN_FEATURE_ID));
					userFeature.setFeatureName(rs.getString(UserFeature.COLUMN_FEATURE_NAME));
					userFeature.setCategoryId(rs.getInt(UserFeature.COLUMN_CATEGORY_ID));
					userFeature.setCategoryName(rs.getString(UserFeature.COLUMN_CATEGORY_NAME));
					features.add(userFeature);
				}
				return features;
			}
		});
	}

	@Override
	public void updateUserFeature(UserFeature feature) {
		
//		String sql = "UPDATE network.feature SET "
		
	}

	@Override
	public void addUserFeature(Integer userId, UserFeature feature) {
		String sql = "INSERT INTO network.feature ("+UserFeature.COLUMN_FEATURE_NAME+", "+UserFeature.COLUMN_CATEGORY_ID+") "
				+ "VALUES('"+feature.getFeatureName()+"', " + feature.getCategoryId() + ") RETURNING id";
		
		Integer featureId = jdbcTemplate.query(sql, new ResultSetExtractor<Integer>() {

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getInt("id");
				}
				return null;
			}
			
		});
		
		if (featureId != null) {
			String sql2 = "INSERT INTO network.user_feature (feature_id, user_id) VALUES (?,?)";
			int rows = jdbcTemplate.update(sql2, featureId, userId);
		}
		
	}

	@Override
	public String setUserImage(Integer userId, String filepath) {
		String sql = "update network.user set image = ? where id = ?";
		int rows = jdbcTemplate.update(sql, filepath, userId);
		if (rows == 1) 
			return "User image successfully updated";
		return "";
	}

	@Override
	public List<User> getUsersInGeofence(Integer userId) {
		
		String sql = "SELECT * FROM network.user WHERE id = any("
				+ "SELECT DISTINCT user_id FROM geo.user_geofence WHERE geofence_id = ANY("
				+ "SELECT geofence_id FROM geo.user_geofence WHERE user_id = ?) AND user_id != ?);";
		List<User> userList = jdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {

			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<User> resultList = new ArrayList<>();
				while(rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt(User.COLUMN_USER_ID));
					user.setUsername(rs.getString(User.COLUMN_USERNAME));
					user.setEmail(rs.getString(User.COLUMN_EMAIL));
					user.setPassword(rs.getString(User.COLUMN_PASSWORD));
					user.setName(rs.getString(User.COLUMN_NAME));
					user.setSurname(rs.getString(User.COLUMN_SURNAME));
					user.setBirthday(rs.getDate(User.COLUMN_BIRTHDAY));
					resultList.add(user);
				}
				return resultList;
			}
			
		}, userId, userId);
		
		return userList;
	}
	
	

}
