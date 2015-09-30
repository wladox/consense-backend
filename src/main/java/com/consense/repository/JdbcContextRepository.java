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

import com.consense.model.context.ActivityItem;
import com.consense.model.context.AppsItem;
import com.consense.model.context.AudioItem;
import com.consense.model.context.ContextItem;
import com.consense.model.context.ContextItemFilter;
import com.consense.model.context.LocationItem;
import com.consense.model.context.MusicItem;
import com.consense.model.context.PedometerItem;

@Repository
public class JdbcContextRepository implements ContextRepository {

	public static final String SCHEMA = "context";
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcContextRepository() {}
	
	@Autowired
	public JdbcContextRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	@Override
	public void addActivityItem(ActivityItem item) {
		String sql = "INSERT INTO " + SCHEMA + "."+ ActivityItem.TABLE_NAME +" (type, user_id, created,"
				+ ActivityItem.COLUMN_NAME + ","
				+ ActivityItem.COLUMN_PROBABILITY
				+ ") VALUES (?,?,?,?,?);";
		
		jdbcTemplate.update(sql, item.getType(), item.getUserId(), item.getCreated(), item.getName(), item.getProbability());
	}

	@Override
	public void addAppsItem(AppsItem item) {
		String sql = "INSERT INTO " + SCHEMA + "."+ AppsItem.TABLE_NAME +" (type, user_id, created,"
				+ AppsItem.COLUMN_NAMES
				+ ") VALUES (?,?,?,?);";
		jdbcTemplate.update(sql, item.getType(), item.getUserId(), item.getCreated(), item.getNames());
	}

	@Override
	public void addAudioItem(AudioItem item) {
		String sql = "INSERT INTO " + SCHEMA + "."+ AudioItem.TABLE_NAME +" (type, user_id, created,"
				+ AudioItem.COLUMN_CLASSIFICATION + ","
				+ AudioItem.COLUMN_PROBABILITY
				+ ") VALUES (?,?,?,?,?);";
		jdbcTemplate.update(sql, item.getType(), item.getUserId(), item.getCreated(), item.getClassification(), item.getProbability());
	}

	@Override
	public void addLocationItem(LocationItem item) {
		String sql = "INSERT INTO " + SCHEMA + "."+ LocationItem.TABLE_NAME +" (type, user_id, created,"
				+ LocationItem.COLUMN_ACCURACY + ","
				+ LocationItem.COLUMN_LAT + ","
				+ LocationItem.COLUMN_LONG + ","
				+ LocationItem.COLUMN_SPEED
				+ ") VALUES (?,?,?,?,?,?,?);";
		jdbcTemplate.update(sql, item.getType(), item.getUserId(), item.getCreated(), item.getAccuracy(), item.getLatitude(), item.getLongitude(), item.getSpeed());
	}

	@Override
	public void addMusicItem(MusicItem item) {
		String sql = "INSERT INTO " + SCHEMA + "."+ MusicItem.TABLE_NAME +" (type, user_id, created,"
				+ MusicItem.COLUMN_ALBUM + ","
				+ MusicItem.COLUMN_AUTHOR + ","
				+ MusicItem.COLUMN_TITLE
				+ ") VALUES (?,?,?,?,?,?);";
		jdbcTemplate.update(sql, item.getType(), item.getUserId(), item.getCreated(), item.getAlbum(), item.getAuthor(), item.getTitle());
	}

	@Override
	public void addPedometerItem(PedometerItem item) {
		String sql = "INSERT INTO " + SCHEMA + "."+ PedometerItem.TABLE_NAME +" (type, user_id, created,"
				+ PedometerItem.COLUMN_STEPS
				+ ") VALUES (?,?,?,?);";
		jdbcTemplate.update(sql, item.getType(), item.getUserId(), item.getCreated(), item.getSteps());
	}

	@Override
	public List<ContextItem> getContextOfUser(Integer userId, ContextItemFilter filter) {
		final int type = filter.getType();
		String sql = "SELECT * FROM context.state_"+ type + " WHERE user_id = ? AND type = ? AND created > ? AND created < ?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<ContextItem>>() {

			@Override
			public List<ContextItem> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<ContextItem> items = new ArrayList<>();
				while(rs.next()) {
					switch (type) {
					case ContextItem.TYPE_ACTIVITY:
						items.add(ActivityItem.getFromResult(rs));
						break;
					case ContextItem.TYPE_APPS:
						items.add(AppsItem.getFromResult(rs));
						break;
					case ContextItem.TYPE_LOCATION:
						items.add(LocationItem.getFromResult(rs));
						break;
					case ContextItem.TYPE_MUSIC:
						items.add(MusicItem.getFromResult(rs));
						break;
//					case 5:
//						items.add(AudioItem.getFromResult(rs));
//						break;
//					case 6:
//						items.add(PedometerItem.getFromResult(rs));
//						break;
					}
					
				}
				return items;
			}
			
		}, userId, type, filter.getFrom(), filter.getTo());
	}

}
