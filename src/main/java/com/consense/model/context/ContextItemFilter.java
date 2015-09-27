package com.consense.model.context;

import java.util.Date;

import com.consense.model.context.ContextItem.ContextType;

public class ContextItemFilter {

	private Date from;
	private Date to;
	private ContextType type;
	
	public ContextItemFilter() {
		
	}
	
	public ContextItemFilter(ContextType type, Date from, Date to) {
		this.from = from;
		this.to = to;
		this.type = type;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public ContextType getType() {
		return type;
	}

	public void setType(ContextType type) {
		this.type = type;
	}
	
	
}
