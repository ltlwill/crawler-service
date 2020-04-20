package com.efe.ms.crawlerservice.service.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efe.ms.crawlerservice.dao.UserDao;
import com.efe.ms.crawlerservice.model.common.User;
import com.efe.ms.crawlerservice.mongorepo.UserRepository;

/**
 * 用户业务接口
 * @author Tianlong Liu
 * @2020年4月9日 上午9:53:29
 */

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findByName(String name) {
		if(StringUtils.isBlank(name)) {
			return null;
		}
		return userRepository.findByName(name);
	}

	@Override
	public List<User> findAllUsers(User user) {
		return userDao.getListByEntity(user);
	}
	

}
