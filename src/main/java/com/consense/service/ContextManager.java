package com.consense.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consense.model.context.ActivityItem;
import com.consense.model.context.AppsItem;
import com.consense.model.context.AudioItem;
import com.consense.model.context.ContextItem;
import com.consense.model.context.ContextItemFilter;
import com.consense.model.context.LocationItem;
import com.consense.model.context.MusicItem;
import com.consense.model.context.PedometerItem;
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
		switch(item.getType()) {
		
		case 1:
			ActivityItem a = (ActivityItem) item;
			contextRepository.addActivityItem(a);
			break;
			
		case 2:
			AppsItem appsItem = (AppsItem) item;
			contextRepository.addAppsItem(appsItem);
			break;
		case 3:
			LocationItem locationItem = (LocationItem) item;
			contextRepository.addLocationItem(locationItem);
			break;
		case 4:
			break;
		case 5:
			break;
		}
		
	}

	@Override
	public List<? extends ContextItem> getContextItems(Integer userId, ContextItemFilter filter) {
		return contextRepository.getContextOfUser(userId, filter);
	}


	@Override
	public void updateUserContext(Integer usedId, JSONArray json) {
		JSONArray jsonArray;
		try {
			jsonArray = json;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject contextStateObject = jsonArray.getJSONObject(i);
				switch(contextStateObject.getString("type")) {
				
				case "activity":
					ActivityItem activityItem = ActivityItem.getFromJSON(contextStateObject);
					activityItem.setUserId(usedId);
					contextRepository.addActivityItem(activityItem);
					break;
				case "apps":
					AppsItem appsItem = AppsItem.getFromJSON(contextStateObject);
					appsItem.setUserId(usedId);
					contextRepository.addAppsItem(appsItem);
					break;
				case "location":
					LocationItem locationItem = LocationItem.getFromJSON(contextStateObject);
					locationItem.setUserId(usedId);
					contextRepository.addLocationItem(locationItem);
					break;
				case "music":
					MusicItem musicItem = MusicItem.getFromJSON(contextStateObject);
					musicItem.setUserId(usedId);
					contextRepository.addMusicItem(musicItem);
					break;
				case "audio":
					AudioItem audioItem = AudioItem.getFromJSON(contextStateObject);
					audioItem.setUserId(usedId);
					contextRepository.addAudioItem(audioItem);
					break;
				case "pedometer":
					PedometerItem pedometerItem = PedometerItem.getFromJSON(contextStateObject);
					pedometerItem.setUserId(usedId);
					contextRepository.addPedometerItem(pedometerItem);
					break;
				}
				
				
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
				
//				contextItem.setParams(contextStateObject.getJSONArray("params").toString());
//				contextItem.setUserId(usedId);
//				contextRepository.addContextItem(contextItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
