package com.consense.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	@RequestMapping("/greeting/")
	private String sayHello (Model model) {
		model.addAttribute("greeting", "Hello world!");
		System.out.println("test");
		return "hello";
	}

}
