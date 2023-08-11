package com.lisnenko.tasktracker.service;

import com.lisnenko.tasktracker.entity.User;
import com.lisnenko.tasktracker.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	void save(WebUser webUser);
}
