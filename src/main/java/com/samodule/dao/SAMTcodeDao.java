package com.samodule.dao;

import java.util.List;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmTcode;
import com.samodule.vo.SapAmTcodeVO;

public interface SAMTcodeDao extends Dao<SapAmTcode>{	
	public List<SapAmTcodeVO> getTocdeByMasterRole(SapAmTcodeVO amTcodeVO);
	

}
