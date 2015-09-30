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
import org.springframework.stereotype.Repository;

import com.consense.model.Geofence;
import com.consense.model.User;

@Repository
public class JdbcGeofenceRepository implements GeofenceRepository {

	private JdbcTemplate jdbcTemplate;

	public JdbcGeofenceRepository() {}

	@Autowired
	public JdbcGeofenceRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Geofence getGeofence() {
		
		return null;
	}

	@Override
	public void addGeofence(Geofence geofence) {

		String sql = "INSERT INTO geo.geofence (radius, name, duration, created, geom) VALUES (?,?,?,?, ST_GeomFromText('POINT(? ?)', 4326)";
		
		jdbcTemplate.update(sql, geofence.getRadius(), geofence.getName(), geofence.getDuration(), 
				geofence.getCreated(), geofence.getLatitude(), geofence.getLongitude());

	}

	@Override
	public List<Geofence> findClosestNeighbours(double lat, double longg) {

		String sql = "SELECT " 
							+ Geofence.COLUMN_ID + "," 
							+ Geofence.COLUMN_NAME + ","
							+ Geofence.COLUMN_DURATION + ","
							+ Geofence.COLUMN_RADIUS + ","
							+ Geofence.COLUMN_CREATED + ","
							+ "ST_x(geom) AS lat, ST_y(geom) AS long"
							+ " FROM "+ Geofence.SCHEMA +"."+ Geofence.TABLE_NAME 
				+ " ORDER BY geom <-> ST_MakePoint(?,?) LIMIT 10;";
		
		Double[] params = {lat, longg};
		
		return jdbcTemplate.query(sql, params, new ResultSetExtractor<List<Geofence>>() {

			@Override
			public List<Geofence> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Geofence> geofences = new ArrayList<>();
				while (rs.next()) {
					
					Geofence geofence = new Geofence();
					geofence.setGeofenceId(rs.getInt(Geofence.COLUMN_ID));
					geofence.setName(rs.getString(Geofence.COLUMN_NAME));
					geofence.setDuration(rs.getLong(Geofence.COLUMN_DURATION));
					geofence.setRadius(rs.getInt(Geofence.COLUMN_RADIUS));
					geofence.setCreated(rs.getDate(Geofence.COLUMN_CREATED));
					geofence.setLatitude(rs.getDouble(Geofence.COLUMN_LAT));
					geofence.setLongitude(rs.getDouble(Geofence.COLUMN_LONG));
					geofences.add(geofence);
				}
				return geofences;
			}

			
		});
	}

	@Override
	public boolean isUserInGeofence(Integer userId, Integer geofenceId) {
		return false;
	}

	@Override
	public List<User> findAllUsersInGeofence(Integer geofenceId) {
		String sql = "SELECT id, username, email, name, surname, birthday FROM " + User.SCHEMA + "." + User.TABLE_NAME 
				+ " JOIN geo.user_geofence ug ON geofence_id = ? AND ug.user_id = id";
		
		Integer[] args = {geofenceId};
		
		return jdbcTemplate.query(sql, args, new ResultSetExtractor<List<User>>() {

			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<User> users = new ArrayList<>();
				while (rs.next()) {
					User user = new User();
					user.setUserId(rs.getInt(User.COLUMN_USER_ID));
					user.setUsername(rs.getString(User.COLUMN_NAME));
					user.setEmail(rs.getString(User.COLUMN_EMAIL));
					user.setName(rs.getString(User.COLUMN_NAME));
					user.setSurname(rs.getString(User.COLUMN_SURNAME));
					user.setBirthday(rs.getDate(User.COLUMN_BIRTHDAY));
					users.add(user);
				}
				return users;
			}
		});
	}

	@Override
	public int addUser2Geofence(Integer userId, Integer geofenceId){

		String sql = "INSERT INTO geo.user_geofence VALUES (?,?, false)";
		return jdbcTemplate.update(sql, geofenceId, userId);
		
	}

	@Override
	public int removeUserFromGeofence(Integer userId, Integer geofenceId) {

		String sql = "DELETE FROM geo.user_geofence WHERE user_id = ? AND geofence_id = ?";
		return jdbcTemplate.update(sql, userId, geofenceId);
		
	}
	
	
	
	
	
	
	

}
