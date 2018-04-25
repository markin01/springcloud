package com.bill99.springboot.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bill99.springboot.mapper.UserMapper;
import com.bill99.springboot.model.User;

@Service
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private UserMapper userMapper;
	
	public List<User> queryUser(){
		List<User> result = userMapper.findAll();
		logger.info("@@UserService queryUser result = {}", result);
		return result;
	}
	
	public int addUser(User user){
		return userMapper.insert(user);
	}
	
}
