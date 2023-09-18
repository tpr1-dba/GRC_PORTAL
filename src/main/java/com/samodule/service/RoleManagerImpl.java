package com.samodule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.RoleDao;
import com.samodule.model.Role;

@Service
public class RoleManagerImpl implements RoleManager {
	@Autowired 
	RoleDao roleDao;
	@Override
	public List<Role> getRoleByLogin(String loginId) {
		// TODO Auto-generated method stub
		return roleDao.getRoleByLogin(loginId);
	}
        
}
