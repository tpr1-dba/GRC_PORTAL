package com.samodule.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.RiskDao;
import com.samodule.model.SapAmRisk;



@Service("riskManager")
public class RiskManagerImpl implements RiskManager {
	Logger log=LoggerFactory.getLogger(getClass());
	@Autowired
	private RiskDao riskDao;
	
	@Transactional
	@Override
	public List<SapAmRisk> getRisk() throws Exception{
		log.info("Get Risk");
		return riskDao.getRisk();
	}
	
}
