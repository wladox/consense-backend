package com.consense.model;

import java.io.Serializable;

public class UserFeature implements Serializable{

	private static final long serialVersionUID = 1641038494547324128L;
	
	public static final String COLUMN_FEATURE_ID 	= "id";
	public static final String COLUMN_FEATURE_NAME 	= "name";
	public static final String COLUMN_CATEGORY_ID	= "cat_id";
	public static final String COLUMN_CATEGORY_NAME	= "cat_name";
	
	private Integer featureId;
	private String 	featureName;
	private Integer categoryId;
	private String	categoryName;
//	private FeatureCategory	category;
	
	
	public Integer getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
//	public FeatureCategory getCategory() {
//		return category;
//	}
//	public void setCategory(FeatureCategory category) {
//		this.category = category;
//	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	
	
	
}
