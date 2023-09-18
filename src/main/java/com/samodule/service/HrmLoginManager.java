package com.samodule.service;

import java.util.List;

import com.samodule.vo.HrmLoginVO;

/**
 * 
 * @author ved.prakash
 * @date 2014-9-2
 * 
 */
public interface HrmLoginManager{
	
	public HrmLoginVO get(HrmLoginVO hrmLogin) throws Exception;
	
	List<HrmLoginVO> getAllEmployee(boolean isRefresh);
	
}
