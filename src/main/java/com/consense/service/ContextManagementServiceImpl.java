package com.consense.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consense.model.ContextItem;
import com.consense.model.Parameter;
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

	@Override
	public void updateUserContext(Integer usedId, String context) {
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(context);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject contextStateObject = jsonArray.getJSONObject(i);
				ContextItem contextItem = new ContextItem();
				contextItem.setItemId(contextStateObject.getInt("id"));
				contextItem.setCreated(new Date(contextStateObject.getLong("timestamp")));
				contextItem.setType(contextStateObject.getString("type"));
				
				List<Parameter> pList = new ArrayList<>();
				JSONArray paramArray = contextStateObject.getJSONArray("params");
				for (int j = 0; j < paramArray.length(); j++) {
					JSONObject paramObject = new JSONObject(paramArray.getString(j));
					Parameter parameter = new Parameter();
					parameter.setName(paramObject.getString("name"));
					parameter.setType(paramObject.getString("type"));
					parameter.setValue(paramObject.getString("value"));
					pList.add(parameter);
				}
				
				contextItem.setParams(pList);
				contextItem.setUserId(usedId);
				contextRepository.addContextStateItem(contextItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	

}
