package com.talk.app.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	//메인
	@GetMapping("admin")
	public String main() {
		return "admin/main";
	}
	
	//복지목록
	@GetMapping("admin/walfare")
	public String walfare() {
		return "admin/walfare";
	}
	
	
}
