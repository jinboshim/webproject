package project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pporan.maven.framework.data.EData;
import project.service.SampleService;


@Controller
public class SampleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SampleService sampleService;
	
	@RequestMapping("/sample.adm")
	public ModelAndView _sample(EData eData) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		sampleService._sample(eData);
		
		mav.addObject("return", "return");
		return mav;
	}
	
	@RequestMapping("/sampleJson")
	public ModelAndView _sampleJson(EData eData) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		
		EData reData = new EData();
		reData.put("key1", "val1");
		reData.put("key2", "val2");
		reData.put("key3", "val3");
		reData.put("key4", "val4");
		
		mav.addObject("data", reData);
		
		return mav;
	}
	
}
