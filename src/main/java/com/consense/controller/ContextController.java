package com.consense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consense.model.context.ContextItem;
import com.consense.service.IContextManager;

@RestController("contextController")
@RequestMapping("/context")
public class ContextController {

	private IContextManager contextManager;

	@Autowired
	public void setContextManager(IContextManager contextManager) {
		this.contextManager = contextManager;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addContextState(@RequestBody ContextItem item) {
		contextManager.addContext(item);
	}
	
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public void updateUserContext(@RequestParam(value="userId") String userId, @RequestParam(value="context") String json) {
		contextManager.updateUserContext(Integer.parseInt(userId), json);
	}
	
	
}
