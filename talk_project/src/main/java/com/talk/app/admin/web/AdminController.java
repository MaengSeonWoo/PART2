package com.talk.app.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.talk.app.admin.service.SampleService;
import com.talk.app.admin.service.WelfareService;
import com.talk.app.admin.service.WelfareVO;


@Controller
public class AdminController {
	
	@Autowired
	WelfareService service;
	
	//메인
	@GetMapping("admin")
	public String main(Model model) {
		return "admin/main";
	}
	
	//복지목록
	@GetMapping("admin/welfare")
	public String walfare(Model model) {
		List<WelfareVO> list = service.welfareList();
		model.addAttribute("welfare",list);
		return "admin/welfare";
	}
	
	
	

	
}
