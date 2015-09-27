package com.consense.service;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consense.model.context.ContextItem;
import com.consense.model.context.ContextItemFilter;
import com.consense.repository.ContextRepository;

@Service
public class ContextManager implements IContextManager{
	
	private ContextRepository contextRepository;

	@Autowired
	public void setContextRepository(ContextRepository contextRepository) {
		this.contextRepository = contextRepository;
	}

	@Override
	public void addContext(ContextItem item) {
		contextRepository.addContextItem(item);
		
	}

	@Override
	public List<ContextItem> getContextItems(Integer userId, ContextItemFilter filter) {
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
				
//				List<Parameter> pList = new ArrayList<>();
//				JSONArray paramArray = contextStateObject.getJSONArray("params");
//				for (int j = 0; j < paramArray.length(); j++) {
//					JSONObject paramObject = new JSONObject(paramArray.getString(j));
//					Parameter parameter = new Parameter();
//					parameter.setName(paramObject.getString("name"));
//					parameter.setType(paramObject.getString("type"));
//					parameter.setValue(paramObject.getString("value"));
//					pList.add(parameter);
//				}
				
				contextItem.setParams(contextStateObject.getString("params"));
				contextItem.setUserId(usedId);
				contextRepository.addContextItem(contextItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
