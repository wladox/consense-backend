package com.consense.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

@XmlRootElement
public class Parameter {

	@XmlElement
	private String name;
	
	@XmlElement
	private String type;
	
	@XmlElement
	private String value;
	
	public Parameter() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		JSONObject param = new JSONObject();
        try {
            param.put("name", getName());
            param.put("type", getType());
            param.put("value", getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return param.toString();
	}
	
}
