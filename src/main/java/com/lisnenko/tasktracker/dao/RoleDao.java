package com.lisnenko.tasktracker.dao;

import com.lisnenko.tasktracker.entity.Role;

public interface RoleDao {

    public Role findRoleByName(String roleName);
}
