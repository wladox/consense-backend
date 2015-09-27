package com.consense.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.consense.model.context.ContextItem;
import com.consense.model.context.ContextItem.ContextType;

@Repository
public class JdbcContextRepository implements ContextRepository {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcContextRepository() {}
	
	@Autowired
	public JdbcContextRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void addContextItem(ContextItem item) {
		String sql = "INSERT INTO context.context_state (type, user_id, created, params ) VALUES (?,?,?,?);";
		System.out.println(item.getParams().toString());
		jdbcTemplate.update(sql, item.getType(), item.getUserId(), item.getCreated(), item.getParams().toString());
	}

	@Override
	public List<ContextItem> getContextOfUser(Integer userId, Integer contextType, Date from, Date to) {

		String sql = "SELECT * FROM context.context_state WHERE user_id = ? AND type = ? AND created > ? AND created < ?";
		jdbcTemplate.query(sql, new ResultSetExtractor<List<ContextItem>>() {

			@Override
			public List<ContextItem> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<ContextItem> items = new ArrayList<>();
				while(rs.next()) {
					ContextItem item = new ContextItem();
					item.setItemId(rs.getInt("context_state_id"));
					item.setType(rs.getString("type"));
					item.setCreated(rs.getTimestamp("created"));
					item.setParams(rs.getString("params"));
					items.add(item);
				}
				return items;
			}
			
		}, userId, ContextType.getValueById(contextType), from, to);
		return null;
	}

}
