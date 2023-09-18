package com.samodule.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samodule.service.SAMTcodeService;
import com.samodule.vo.SapAmTcodeVO;

@Controller
public class TcodeMasterController {
	private static final Logger logger = LoggerFactory.getLogger(TcodeMasterController.class);


	@Autowired
	SAMTcodeService samTcodeService;

	
	@RequestMapping(value = "/getTcodeByRoles",  headers = "Accept=application/json", method = RequestMethod.POST)
	public @ResponseBody String getTcodeByRoles(@RequestBody   SapAmTcodeVO amTcodeVO) {	
		try {
             System.out.println(amTcodeVO.toString());
			List<SapAmTcodeVO> tcodeInfos = null;
			try {
				tcodeInfos = samTcodeService.getTcodeByRoles(amTcodeVO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			// : 1;List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
			logger.info("getTocde   XXXXxX  "+tcodeInfos.size());
			// System.out.println(sortDirection + " v " + sortColumnIndex +
			// " v "
			// + criteria.sColumns + " vv " + items.get(sortColumnIndex));
			return gson.toJson(tcodeInfos);
				

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
