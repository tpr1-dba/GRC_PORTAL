package com.samodule.service;

import java.util.List;

import com.samodule.vo.SapAmTcodeVO;

public interface SAMTcodeService {

	List<SapAmTcodeVO> getTcodeByRoles(SapAmTcodeVO amTcodeVO);
	

}
