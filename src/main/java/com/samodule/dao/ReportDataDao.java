package com.samodule.dao;

import java.util.List;

import com.samodule.model.SapAmUserRequest;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.ReportDataDto;
import com.samodule.vo.ReportRequestDto;

public interface ReportDataDao extends Dao<SapAmUserRequest> {

	List<ReportDataDto> getReportDataList(ReportRequestDto reqDto, HeapUserDetails principal) throws Exception;

}
