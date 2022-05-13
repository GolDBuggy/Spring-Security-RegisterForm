package com.java.myblogproject.dao;

import com.java.myblogproject.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
