package com.samodule.controller;



import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.security.HeapUserDetails;
import com.samodule.service.ReportService;
import com.samodule.vo.ReportRequestDto;

@Controller
public class ReportsController {
	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

	@Autowired
	private ReportService reproService;
	
	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public ModelAndView reports() {
		System.out.println("reports");
		logger.info("reports");
		return new ModelAndView("reports");
	}
	
	@RequestMapping(value = "/downloadExcel", method = RequestMethod.POST)
	public void downloadExcel(@ModelAttribute ReportRequestDto reqDto, HttpServletResponse response, Authentication authentication) {
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				principal = (HeapUserDetails) authentication.getPrincipal();
				 // Set response headers
			    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			    response.setHeader("Content-Disposition", "attachment; filename=ReportDownload_"+System.currentTimeMillis()+".xlsx");
			    reproService.downloadReport(response.getOutputStream(), reqDto, principal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


}
