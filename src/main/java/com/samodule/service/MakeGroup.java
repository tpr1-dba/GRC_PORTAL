package com.samodule.service;

import java.util.List;
import java.util.Map;

import com.samodule.vo.RoleMasterVO;

@FunctionalInterface
interface MakeGroup {
	public Map<String, List<String>> groupBy(List<RoleMasterVO> userRoles );
}

