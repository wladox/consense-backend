package com.consense.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.consense.model.ContextItem;

@Repository
public class JdbcContextRepository implements ContextRepository {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcContextRepository() {}
	
	@Autowired
	public JdbcContextRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void addContextStateItem(ContextItem item) {
		String sql = "INSERT INTO context.context_state (type, user_id, created, params ) VALUES (?,?,?,?);";
		System.out.println(item.getParams().toString());
		jdbcTemplate.update(sql, item.getType(), item.getUserId(), item.getCreated(), item.getParams().toString());
	}

}
