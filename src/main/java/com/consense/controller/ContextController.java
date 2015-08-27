package com.consense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.consense.model.ContextItem;
import com.consense.service.ContextManagementService;

@RestController("contextController")
@RequestMapping("/context")
public class ContextController {

	private ContextManagementService contextManager;

	@Autowired
	public void setContextManager(ContextManagementService contextManager) {
		this.contextManager = contextManager;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addContextState(@RequestBody ContextItem item) {
		
		contextManager.addContextState(item);
	}
	
	
}
