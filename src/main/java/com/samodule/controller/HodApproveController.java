package com.samodule.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.samodule.service.ApproveRequestManager;
import com.samodule.service.HodApproveManager;
import com.samodule.vo.AssignedRolesVO;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmUserAssignedRoleVO;

@Controller
public class HodApproveController {
	private static final Logger logger = LoggerFactory.getLogger(HodApproveController.class);

	@Autowired
	private HodApproveManager hodApproveManager;
	@Autowired
	private ApproveRequestManager approveRequestManager;
	

	@RequestMapping(value = "/approvetrequest", method = RequestMethod.GET)
	public ModelAndView approvetcode() {
		System.out.println("approvetrequest");
		logger.info("approvetrequest");
		return new ModelAndView("approvetrequest");
	}

	@RequestMapping(value = "/getHodRequest", method = RequestMethod.GET)
	public @ResponseBody String getUserRequest(@TableParam JQueryDataTableParamModel criteria,
			Authentication authentication) {
		try {
			List<SAPUserRequestVO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = hodApproveManager.getUserRequest(criteria, principal);
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
	
	@RequestMapping(value = "/getAssignedRole/{requestId}/{surRecid}", headers = "Accept=application/json")
	public @ResponseBody String getAssignedRole(@PathVariable("requestId") long requestId,@PathVariable("surRecid") long surRecid,Authentication authentication, @TableParam JQueryDataTableParamModel criteria,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("SapAmRoleMaster   " + requestId);
		System.out.println("   XXXXxX  " + surRecid);
		
		// SapAmRoleMaster sapAmRoleMaster
		List<SapAmUserAssignedRoleVO> amUserAssignedRoles = null;
		
		
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();

			try {

				amUserAssignedRoles = hodApproveManager.getAssignedRoleWIthSensetive(principal,requestId,surRecid,criteria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// : 1;
		//List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
		logger.info("amRoleMasters   XXXXxX  "+amUserAssignedRoles.size());
	
		return gson.toJson(ControllerUtils.getWebResultSet(criteria,
				new ResultSet(amUserAssignedRoles, new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
	}
	
	@RequestMapping(value = "/updateRequestHOD", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> updateRequestHOD(Authentication authentication,
			@RequestBody AssignedRolesVO assignedRolesVO) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Tcode Request " + assignedRolesVO.toString());

		
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					hodApproveManager.updateRequestHOD(assignedRolesVO, principal);
					response.put("ok", "Data saved succesufully");
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
	
	@RequestMapping(value = "/updateAssignedRoles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> updateAssignedRoles(Authentication authentication,
			@RequestBody AssignedRolesVO assignedRolesVO) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Tcode Request " + assignedRolesVO.toString());

		
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					hodApproveManager.updateAssignedRoles(assignedRolesVO, principal);
					response.put("ok", "Data saved succesufully");
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
	
	
	@RequestMapping(value = "/saveAssignedRoles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ResponseEntity<Map<String, String>> saveAssignedRoles(Authentication authentication,
//			@RequestBody AssignedRolesVO assignedRolesVO) {
		public @ResponseBody ResponseEntity<Map<String, String>> saveAssignedRoles(Authentication authentication,
				@RequestBody SapAmRolesAssignVO sapAmRolesAssignVO) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("saveAssignedRoles " + sapAmRolesAssignVO);

		
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					//hodApproveManager.saveAssignedRoles(assignedRolesVO, principal);
					hodApproveManager.saveAssignedRoles(sapAmRolesAssignVO, principal);
					response.put("ok", "Data saved succesufully");
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
