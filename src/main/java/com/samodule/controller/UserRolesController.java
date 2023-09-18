package com.samodule.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samodule.datatable.ControllerUtils;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.datatable.ResultSet;
import com.samodule.datatable.TableParam;
import com.samodule.model.SapAmRoleMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.service.UserRolesManager;
import com.samodule.vo.SapAmRolesRequestVO;

@Controller
public class UserRolesController {
	private static final Logger logger = LoggerFactory.getLogger(UserRolesController.class);
	@Autowired
	private UserRolesManager userRolesManager;

	@RequestMapping(value = "/getRolesByTcode", headers = "Accept=application/json", method = RequestMethod.POST)
	public @ResponseBody String getRolesByTcode(@RequestBody SapAmRolesRequestVO sapAmRolesRequestVO,Authentication authentication, @TableParam JQueryDataTableParamModel criteria,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("SapAmRoleMaster   " + sapAmRolesRequestVO.toString());
		System.out.println("   XXXXxX  " + sapAmRolesRequestVO.toString());

		// SapAmRoleMaster sapAmRoleMaster
		List<SapAmRoleMaster> amRoleMasters = null;
		if (sapAmRolesRequestVO!=null &&(authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();

			try {

				amRoleMasters = userRolesManager.getRolesByTcode(principal.getSapEntityCode(),sapAmRolesRequestVO,criteria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// : 1;
		//List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
		logger.info("amRoleMasters   XXXXxX  "+amRoleMasters.size());
		// System.out.println(sortDirection + " v " + sortColumnIndex +
		// " v "
		// + criteria.sColumns + " vv " + items.get(sortColumnIndex));
		criteria.sEcho="0";
		return gson.toJson(ControllerUtils.getWebResultSet(criteria,
				new ResultSet(amRoleMasters, new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
	}
	@RequestMapping(value = "/getRolesByTcodes/{requestId}/{surRecid}/{surtRecids}", headers = "Accept=application/json")
	public @ResponseBody String getRolesByTcodes(@PathVariable("requestId") String requestId,@PathVariable("surRecid") String surRecid,@PathVariable("surtRecids") String surtRecids,Authentication authentication, @TableParam JQueryDataTableParamModel criteria,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("SapAmRoleMaster   " + requestId);
		System.out.println("   XXXXxX  " + surtRecids);
		Supplier<List<Long>> supplier = () -> new LinkedList<Long>();
		List<Long> surtRecid=Arrays.stream(surtRecids.split(",")).map(a ->Long.parseLong(a)).collect(Collectors.toCollection(supplier));
		// SapAmRoleMaster sapAmRoleMaster
		List<SapAmRolesRequestVO> amRoleMasters = null;
		SapAmRolesRequestVO sapAmRolesRequestVO=new SapAmRolesRequestVO();
		//sapAmRolesRequestVO.setHrisCode(surtRecids);
		sapAmRolesRequestVO.setRequestId(new BigDecimal(requestId));
		sapAmRolesRequestVO.setSurRecid(new BigDecimal(surRecid));
		sapAmRolesRequestVO.setSurtRecids(surtRecid);
		
		if (sapAmRolesRequestVO!=null &&(authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();

			try {

				amRoleMasters = userRolesManager.getRolesByTcodes(principal,sapAmRolesRequestVO,criteria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// : 1;
		//List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
		logger.info("amRoleMasters   XXXXxX  "+amRoleMasters.size());
	
		return gson.toJson(ControllerUtils.getWebResultSet(criteria,
				new ResultSet(amRoleMasters, new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
	}
	
	
	@RequestMapping(value = "/getRolesByRequest/{requestId}/{surRecid}", headers = "Accept=application/json")
	public @ResponseBody String getRolesByRequest(@PathVariable("requestId") long requestId,@PathVariable("surRecid") long surRecid,Authentication authentication, @TableParam JQueryDataTableParamModel criteria,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("SapAmRoleMaster   " + requestId);
		System.out.println("   XXXXxX  " + surRecid);
		
		// SapAmRoleMaster sapAmRoleMaster
		List<SapAmRolesRequestVO> amRoleMasters = null;
		
		
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();

			try {

				amRoleMasters = userRolesManager.getRolesByRequest(principal,requestId,surRecid,criteria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// : 1;
		//List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
		logger.info("amRoleMasters   XXXXxX  "+amRoleMasters.size());
	
		return gson.toJson(ControllerUtils.getWebResultSet(criteria,
				new ResultSet(amRoleMasters, new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
	}
	
	@RequestMapping(value = "/getRolesByRequestEmpCode/{requestId}/{surRecid}", headers = "Accept=application/json")
	public @ResponseBody String getRolesByRequestEmpCode(@PathVariable("requestId") long requestId,@PathVariable("surRecid") long surRecid,Authentication authentication, @TableParam JQueryDataTableParamModel criteria,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("SapAmRoleMaster   " + requestId);
		System.out.println("   XXXXxX  " + surRecid);
		
		// SapAmRoleMaster sapAmRoleMaster
		List<SapAmRolesRequestVO> amRoleMasters = null;
		
		
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();

			try {

				amRoleMasters = userRolesManager.getRolesByRequestEmpCode(principal,requestId,surRecid,criteria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// : 1;
		//List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
		logger.info("amRoleMasters   XXXXxX  "+amRoleMasters.size());
	
		return gson.toJson(ControllerUtils.getWebResultSet(criteria,
				new ResultSet(amRoleMasters, new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
	}
	@RequestMapping(value = "/getRolesByRequestEHOD/{requestId}/{surRecid}", headers = "Accept=application/json")
	public @ResponseBody String getRolesByRequestEHOD(@PathVariable("requestId") long requestId,@PathVariable("surRecid") long surRecid,Authentication authentication, @TableParam JQueryDataTableParamModel criteria,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("SapAmRoleMaster   " + requestId);
		System.out.println("   XXXXxX  " + surRecid);
		
		// SapAmRoleMaster sapAmRoleMaster
		List<SapAmRolesRequestVO> amRoleMasters = null;
		
		
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();

			try {

				amRoleMasters = userRolesManager.getRolesByRequest(principal,requestId,surRecid,criteria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// : 1;
		//List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
		logger.info("amRoleMasters   XXXXxX  "+amRoleMasters.size());
	
		return gson.toJson(ControllerUtils.getWebResultSet(criteria,
				new ResultSet(amRoleMasters, new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
	}
	
	
}
