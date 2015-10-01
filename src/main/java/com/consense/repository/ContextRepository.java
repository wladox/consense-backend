package com.consense.repository;

import java.util.List;

import com.consense.model.context.ActivityItem;
import com.consense.model.context.AppsItem;
import com.consense.model.context.AudioItem;
import com.consense.model.context.ContextItem;
import com.consense.model.context.ContextItemFilter;
import com.consense.model.context.LocationItem;
import com.consense.model.context.MusicItem;
import com.consense.model.context.PedometerItem;

public interface ContextRepository {

	void addActivityItem(ActivityItem item);
	void addAppsItem(AppsItem item);
	void addAudioItem(AudioItem item);
	void addLocationItem(LocationItem item);
	void addMusicItem(MusicItem item);
	void addPedometerItem(PedometerItem item);
	List<? extends ContextItem> getContextOfUser(Integer userId, ContextItemFilter filter);
	
}
