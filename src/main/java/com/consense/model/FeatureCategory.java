package com.consense.model;

public class FeatureCategory {

	public static final String COLUMN_CATEGORY_ID 	= "id";
	public static final String COLUMN_CATEGORY_NAME = "name";
	public static final String COLUMN_CATEGORY_DESC = "description";
	
	private Integer categoryId;
	private String name;
	private String description;
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
