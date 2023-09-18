package com.samodule.dao;

import java.util.List;

import com.samodule.model.Role;

public interface RoleDao {
	public List<Role> getRoleByLogin(String loginId);
}
