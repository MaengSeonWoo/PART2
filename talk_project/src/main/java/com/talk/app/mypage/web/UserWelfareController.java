package com.talk.app.mypage.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.mypage.service.UserWelfareService;

@Controller
public class UserWelfareController {
	
	private UserWelfareService userwelfareService;
	
	@Autowired
	public UserWelfareController(UserWelfareService userwelfareService) {
		this.userwelfareService = userwelfareService;
	}
	
	@GetMapping("userwelfarelist")
	public String userwelfareList(Model model, Principal principal) {
		String userId = principal.getName();
		
		List<WelfareVO> list = userwelfareService.userWelfareList(userId);
		model.addAttribute("userwelfarelist", list);
		
		return "mypage/userwelfareList";
	}
}
