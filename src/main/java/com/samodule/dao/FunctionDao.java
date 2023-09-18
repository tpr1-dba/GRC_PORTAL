package com.samodule.dao;

import java.util.List;
import java.util.Set;

import com.samodule.model.SapAmFunction;

public interface FunctionDao extends Dao<SapAmFunction> {

	public List<SapAmFunction> getFunction()
			throws Exception;

	List<String> getFunctionByRoles(Set<String> roles) throws Exception;

	List<String> getFunctionByRole(String role) throws Exception;

}
