package com.samodule.service;

import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.samodule.dao.ReportDataDao;
import com.samodule.dao.ReportDataDaoImpl;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.ReportDataDto;
import com.samodule.vo.ReportRequestDto;

@Service
public class ReportServiceImpl implements ReportService {

	static final Logger log = Logger.getLogger(ReportServiceImpl.class.getName());

	@Autowired
	private ReportDataDao reportDataDao;

	@Override
	public void downloadReport(OutputStream out, ReportRequestDto reqDto, HeapUserDetails principal) {
		String[] headrs = createReprotHeaders();

		List<ReportDataDto> list = null;
		try(Workbook workbook = new XSSFWorkbook()) {

			// Create Excel workbook and sheet
			Sheet sheet = workbook.createSheet("Report");

			// Create header row
			Row headerRow = sheet.createRow(0);

			for (int i = 0; i < headrs.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headrs[i]);
			}

			list = reportDataDao.getReportDataList(reqDto, principal);

			if (!CollectionUtils.isEmpty(list)) {
				AtomicInteger rowNum = new AtomicInteger(1);
				// Populate data from ResultSet into Excel sheet
				list.forEach(dto -> {
					Row row = sheet.createRow(rowNum.getAndIncrement());
					int i = 0;
					setCellValueByRow(i++, row, dto.getRequestId());
					setCellValueByRow(i++, row, dto.getRequesterName());
					setCellValueByRow(i++, row, dto.getRequesterSapId());
					setCellValueByRow(i++, row, dto.getFinalApprovedBasis());
					setCellValueByRow(i++, row, dto.getApprovalRemarks());
					setCellValueByRow(i++, row, dto.getRequestReason());
					setCellValueByRow(i++, row, dto.getRequestTcode());
					setCellValueByRow(i++, row, dto.getMetigationYn());
					setCellValueByRow(i++, row, dto.getStatus());
					setCellValueByRow(i++, row, dto.getPendingDueDays());
				});
			}
			
			// Write Excel workbook to output stream
			workbook.write(out);
			workbook.close();
			out.close();
		} catch (Exception e) {
			log.error("ERROR >> ReportServiceImpl >> downloadReport", e);
			e.printStackTrace();
		}

	}

	private void setCellValueByRow(int i, Row row, Object data) {
		Cell cell = row.createCell(i);
		cell.setCellValue(String.valueOf(data));
	}

	private String[] createReprotHeaders() {
		String[] headers = { "Request Id", "Requester Name", "Requester SAP ID", "Final Approved Basis",
				"Approval Remarks", "Request Reason", "Request Tcode", "Metigation Y/N","Status","Pending Due Days" };
		return headers;
	}

}
