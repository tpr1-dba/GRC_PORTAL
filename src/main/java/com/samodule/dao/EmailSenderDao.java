package com.samodule.dao;

import java.math.BigDecimal;

public interface EmailSenderDao {

	void sentEmail(BigDecimal requestId, String status1, String status2, int wfLevel, String empCode,int wfLevel1) throws Exception;
	
}
