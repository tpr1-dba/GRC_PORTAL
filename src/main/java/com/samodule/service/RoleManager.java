package com.samodule.service;

import java.util.List;

import com.samodule.model.Role;



public interface RoleManager {
    public List<Role> getRoleByLogin(String loginId);
}
