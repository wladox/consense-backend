package com.consense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consense.model.ContextItem;
import com.consense.repository.ContextRepository;

@Service
public class ContextManagementServiceImpl implements ContextManagementService{
	
	private ContextRepository contextRepository;

	@Autowired
	public void setContextRepository(ContextRepository contextRepository) {
		this.contextRepository = contextRepository;
	}

	@Override
	public void addContextState(ContextItem item) {
		contextRepository.addContextStateItem(item);
		
	}

	@Override
	public List<ContextItem> getContextItemsOfUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextItem getContextItem(Integer userId, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
