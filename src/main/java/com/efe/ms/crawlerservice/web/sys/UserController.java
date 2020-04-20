package com.efe.ms.crawlerservice.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.crawlerservice.model.common.User;
import com.efe.ms.crawlerservice.service.common.UserService;
import com.efe.ms.crawlerservice.web.common.BaseController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping
	public List<User> findAll(){
		return userService.findAll();
	}
	
	@RequestMapping("/{name}")
	public List<User> findByName(@PathVariable String name){
		return userService.findByName(name);
	}
	
	@RequestMapping("/all")
	public List<User> findAllUsers(User user){
		return userService.findAllUsers(user);
	}

	
}
