package com.bill99.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bill99.springboot.model.User;
import com.bill99.springboot.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {
	
	@Autowired
	private UserService userService;

	@Test
	public void queryUser() {
		userService.queryUser();
	}
	
	@Test
	public void addUser() {
		User user = new User();
		user.setName("paglili");
		user.setPassword("123456");
		userService.addUser(user);
	}

}
