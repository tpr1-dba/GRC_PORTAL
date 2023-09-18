package com.samodule.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samodule.datatable.ControllerUtils;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.datatable.ResultSet;
import com.samodule.datatable.TableParam;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.security.HeapUserDetails;
import com.samodule.service.RequestHistoryManager;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmUserRequestApprovalVO;

@Controller
public class RequestHistoryController {
	private static final Logger logger = LoggerFactory.getLogger(RequestHistoryController.class);

	@Autowired
	private RequestHistoryManager requestHistoryManager;
	
	@RequestMapping(value = "/requestHistory", method = RequestMethod.GET)
	public ModelAndView approvetcode() {
		System.out.println("requestHistory");
		logger.info("requestHistory");
		return new ModelAndView("requestHistory");
	}

	@RequestMapping(value = "/getUserRequestHistory", method = RequestMethod.GET)
	public @ResponseBody String getUserRequestHistory(@TableParam JQueryDataTableParamModel criteria,
			Authentication authentication) {
		try {
			List<SAPUserRequestVO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = requestHistoryManager.getUserRequest(criteria, principal);
				}
			}

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			List<String> items = new ArrayList<>();
			if(Objects.nonNull(criteria.sColumns)) {items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));}
			logger.info("getTocde   XXXXxX  " + sodUserRequests.size());
			 String prettyJson =  gson.toJson(ControllerUtils.getWebResultSet(criteria, new ResultSet(sodUserRequests,
						new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
			System.out.println(prettyJson);
			return gson.toJson(ControllerUtils.getWebResultSet(criteria, new ResultSet(sodUserRequests,
					new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/getUserRequestStatus/{surRecid}/{requestId}", method = RequestMethod.GET)
	public @ResponseBody String getUserRequestStatus(@PathVariable Long surRecid,@PathVariable Long requestId,@TableParam JQueryDataTableParamModel criteria,
			Authentication authentication) {
		try {
			List<SapAmUserRequestApprovalVO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());  
					sodUserRequests = requestHistoryManager.getUserRequestStatus(surRecid,requestId,criteria, principal);
				}
			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			List<String> items = new ArrayList<>();
			if(Objects.nonNull(criteria.sColumns)) {items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));}
			
			//logger.info("getTocde   XXXXxX  " + sodUserRequests.size());
			String prettyJson =  gson.toJson(ControllerUtils.getWebResultSet(criteria, new ResultSet(sodUserRequests,
					new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
		System.out.println("*****************************************getUserRequestStatus prettyJson:"+prettyJson);
		
			return gson.toJson(ControllerUtils.getWebResultSet(criteria, new ResultSet(sodUserRequests,
					new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
