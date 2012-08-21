package com.metacube.ipathshala.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.manager.DataUploadTestManager;

/**
 * @author sparakh
 * 
 */
@Controller
public class DataUploadTestController extends AbstractController {
@Autowired
private DataUploadTestManager man;


	@RequestMapping("/getStudents.do")
	@ResponseBody
	public List<String> loadCache()
			throws AppException {
		

		return man.getStudents();
	}
	
	@RequestMapping("/getNewStudents.do")
	@ResponseBody
	public List<String> get()
			throws AppException {
		
		return man.getNewStudents();
	}
	
	

 
}
