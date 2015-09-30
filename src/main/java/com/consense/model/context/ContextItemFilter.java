package com.consense.model.context;

import java.util.Date;


public class ContextItemFilter {

	private Date from;
	private Date to;
	private int type;
	
	public ContextItemFilter() {
		
	}
	
	public ContextItemFilter(int type, Date from, Date to) {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
