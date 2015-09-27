package com.consense.service;

import java.util.List;

import com.consense.model.context.ContextItem;
import com.consense.model.context.ContextItemFilter;


public interface IContextManager {

	void addContext(ContextItem item);
	void updateUserContext(Integer usedId, String context);
	
	List<ContextItem>	getContextItems(Integer userId, ContextItemFilter filter);
	
	
}
