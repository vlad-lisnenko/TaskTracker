package com.lisnenko.tasktracker.dao;

import com.lisnenko.tasktracker.entity.User;

public interface UserDao {

    User findByUserName(String userName);
}
