package com.samodule.dao;

import java.util.List;

import com.samodule.model.HrmLogin;
import com.samodule.vo.HrmLoginVO;


/**
 * 
 * @author Ved prakash
 * @date 2013-9-2
 * 
 */
public interface HrmLoginDao extends Dao<HrmLogin>{	
	public HrmLoginVO get(HrmLoginVO  hrmLogin) throws Exception;

	List<HrmLoginVO> getAllEmployee();
}
