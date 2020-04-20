package com.efe.ms.crawlerservice.service.common;

import java.util.List;

import com.efe.ms.crawlerservice.model.common.User;

public interface UserService {

	List<User> findAll();
	
	List<User> findByName(String name);
	
	List<User> findAllUsers(User user);
}
