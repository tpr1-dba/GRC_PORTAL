package com.samodule.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samodule.datatable.ControllerUtils;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.datatable.ResultSet;
import com.samodule.datatable.TableParam;
import com.samodule.security.HeapUserDetails;
import com.samodule.service.EmpHodApproveManager;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;

@Controller
public class EmpHodApproveController {
	private static final Logger logger = LoggerFactory.getLogger(EmpHodApproveController.class);

	@Autowired
	private EmpHodApproveManager empHodApproveManager;
	
	

  @RequestMapping(value = "/approveEmpHodRequest", method = RequestMethod.GET)
	public ModelAndView approveEmpHodRequest() {
		System.out.println("approveEmpHod");
		logger.info("approvetrequest");
		return new ModelAndView("approveEmpHod");
	}

	@RequestMapping(value = "/getEmpHodRequest", method = RequestMethod.GET)
	public @ResponseBody String getEHodRequest(@TableParam JQueryDataTableParamModel criteria,
			Authentication authentication) {
		try {
			List<SAPUserRequestVO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = empHodApproveManager.getUserEHodRequest(criteria, principal);
				}
			}

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
			logger.info("getTocde   XXXXxX  " + sodUserRequests.size());

			return gson.toJson(ControllerUtils.getWebResultSet(criteria, new ResultSet(sodUserRequests,
					new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/updateRequestEmpHod", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> updateRequestHOD(Authentication authentication,
			@RequestBody SapAmRolesAssignVO sapAmRolesAssignVO) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Tcode Request " + sapAmRolesAssignVO.toString());

		
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					if("A".equalsIgnoreCase(sapAmRolesAssignVO.getStatus()))
					empHodApproveManager.approveRequest(sapAmRolesAssignVO, principal);
					else if("R".equalsIgnoreCase(sapAmRolesAssignVO.getStatus()))
					empHodApproveManager.rejectRequest(sapAmRolesAssignVO, principal);
					response.put("ok", "Request submited succesufully");
					return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
				}
			}
		} catch (Exception e) {	
			logger.error("error   "+e);
			e.printStackTrace();
			response.put("error", "Error while saving request");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
