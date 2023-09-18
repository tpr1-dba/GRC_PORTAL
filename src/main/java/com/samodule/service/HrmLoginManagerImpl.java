package com.samodule.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.samodule.dao.HrmLoginDao;
import com.samodule.vo.HrmLoginVO;



/**
 * 
 * @author ved.prakash
 * @date 2015-9-2
 * 
 */
@Service("hrmloginmanager")
public class HrmLoginManagerImpl implements HrmLoginManager {
	private static final long serialVersionUID = -6304815635978981819L;
	@Resource(name = "hrmLoginDao")
	private HrmLoginDao hrmLoginDao;
	
	private static List<HrmLoginVO> hrmList=new ArrayList<>();

	public void setDao(HrmLoginDao dao) {
		this.hrmLoginDao = dao;
	}

	public HrmLoginDao getDao() {
		return (HrmLoginDao) this.hrmLoginDao;
	}

	public HrmLoginVO get(HrmLoginVO hrmLogin) throws Exception {
		return hrmLoginDao.get(hrmLogin);
	}

	@Override
	public List<HrmLoginVO> getAllEmployee(boolean isRefresh) {
		if(CollectionUtils.isEmpty(hrmList) || Boolean.TRUE == isRefresh) {
			hrmList = hrmLoginDao.getAllEmployee();
		}
		return hrmList;
	}
	
	

	

	
}
