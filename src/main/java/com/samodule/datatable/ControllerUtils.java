package com.samodule.datatable;

import com.samodule.vo.DomainObject;



public class ControllerUtils {
	public static <T extends DomainObject> WebResultSet<T> getWebResultSet(JQueryDataTableParamModel pc, ResultSet<T> rs)
	{
	  return new DataTablesResultSet<T>(pc, rs);
	}
}
