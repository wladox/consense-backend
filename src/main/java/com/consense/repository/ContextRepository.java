package com.consense.repository;

import java.util.Date;
import java.util.List;

import com.consense.model.context.ContextItem;

public interface ContextRepository {

	void addContextItem(ContextItem item);
	List<ContextItem> getContextOfUser(Integer userId, Integer contextType, Date from, Date to);
	
}
