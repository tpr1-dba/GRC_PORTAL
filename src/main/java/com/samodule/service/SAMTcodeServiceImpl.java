package com.samodule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.SAMTcodeDao;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.vo.SapAmTcodeVO;

@Service("samTcodeService")
public class SAMTcodeServiceImpl implements SAMTcodeService {
	
	@Autowired
	SAMTcodeDao samTcodeDao;


	@Override
	public List<SapAmTcodeVO> getTcodeByRoles(SapAmTcodeVO amTcodeVO) {
		// TODO Auto-generated method stub
		return samTcodeDao.getTocdeByMasterRole(amTcodeVO);
	}
	
}
