package com.samodule.service;

import java.io.OutputStream;

import com.samodule.security.HeapUserDetails;
import com.samodule.vo.ReportRequestDto;

public interface ReportService {

	void downloadReport(OutputStream out, ReportRequestDto reqDto, HeapUserDetails principal);

}
