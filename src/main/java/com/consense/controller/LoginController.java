package com.consense.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consense.model.User;


@RestController
public class LoginController {

//	private static final String template = "Hello, %s!";
//	private final AtomicLong counter = new AtomicLong();
//	
//	@RequestMapping(value = "/users", method=RequestMethod.GET)
//	public User findUser(@RequestParam(value="name", defaultValue = "World") String name, 
//						@RequestParam(value="email", defaultValue = "mustermann@mail.de") String email) {
//		return new User(1, name, email);
//	}
//	
//	@RequestMapping("/userList")
//	public List<User> getAllUsers() {
//		
//		List<User> users = new ArrayList<User>();
//		User u1 = new User(1, "wladi", "mustermann@web.de");
//		User u2 = new User(2, "johny", "johny@web.de");
//		User u3 = new User(3, "ronny", "ronny@web.de");
//		users.add(u1);
//		users.add(u2);
//		users.add(u3);
//		
//		return users;
//	}
}
