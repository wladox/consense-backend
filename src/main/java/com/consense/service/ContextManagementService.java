package com.consense.service;

import java.util.List;

import com.consense.model.ContextItem;

public interface ContextManagementService {

	void addContextState(ContextItem item);
	List<ContextItem> getContextItemsOfUser(Integer userId);
	ContextItem	getContextItem(Integer userId, String type);
	void updateUserContext(Integer usedId, String context);
	
}
