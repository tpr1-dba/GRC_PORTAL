package com.samodule.dao;

import java.util.List;

import com.samodule.model.SapAmRisk;

public interface RiskDao extends Dao<SapAmRisk> {

	public List<SapAmRisk> getRisk()
			throws Exception;

}
