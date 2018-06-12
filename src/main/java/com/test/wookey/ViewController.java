package com.test.wookey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.test.service.ImageService;

@Controller
public class ViewController {
	
	private static final Logger logger = LoggerFactory.getLogger(ViewController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView imageTest() {
		ModelAndView mav = new ModelAndView("index");
		Map<String, ArrayList<String>> mapList = new HashMap<String, ArrayList<String>>();

		ArrayList<String> testList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			testList.add((i + 1) + "wookey");
		}

		mapList.put("test", testList);
		mav.addObject("mapList", mapList);
		logger.info("### ¸ÊÁ¤º¸ ###");
		logger.info(mapList.toString());
		return mav;
	}
}
