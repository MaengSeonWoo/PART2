package com.talk.app.admin.welfare.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.talk.app.admin.welfare.service.SampleService;
import com.talk.app.admin.welfare.service.WelfareService;
import com.talk.app.admin.welfare.vo.WelfareVO;

@Controller
public class AdminController {
	
	@Autowired
	SampleService service;
	
	//메인
	@GetMapping("admin")
	public String main() {
		return "admin/main";
	}
	
	//복지목록
	@GetMapping("admin/welfare")
	public String wㄷlfare() {
		return "admin/welfare";
	}
	
	@PostMapping("admin/insert")
	public String insertId(WelfareVO vo) {
		try {
			String no =  service.fetchAndSaveServId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/";
	}
	
	
}
