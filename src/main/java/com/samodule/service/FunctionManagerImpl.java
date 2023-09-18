package com.samodule.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.FunctionDao;
import com.samodule.model.SapAmFunction;



@Service("functionManager")
public class FunctionManagerImpl implements FunctionManager {
	@Autowired
	private FunctionDao functionDao;
	
	@Transactional
	@Override
	public List<SapAmFunction> getFunction() throws Exception{
		return functionDao.getFunction();
	}
	
}
