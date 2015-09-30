package com.consense.service;

import java.util.List;

import org.json.JSONArray;

import com.consense.model.context.ContextItem;
import com.consense.model.context.ContextItemFilter;


public interface IContextManager {

	void addContext(ContextItem item);
	void updateUserContext(Integer usedId, JSONArray context);
	
	List<ContextItem>	getContextItems(Integer userId, ContextItemFilter filter);
	
	
}
