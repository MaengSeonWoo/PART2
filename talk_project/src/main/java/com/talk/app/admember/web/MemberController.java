package com.talk.app.admember.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.talk.app.admember.service.MemberService;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

@Controller
@RequestMapping("admin/approve")
public class MemberController {

	@Autowired
	MemberService service;
	
	//승인목록(기업목록 전체 신청desc처리)
	@GetMapping("")
	public String approveAll(Model model, CoUserVO vol) {
		List<CoUserVO> list = service.approveAll();
		model.addAttribute("list",list);
		return "admember/approvelist";
	}
	
	//일반회원전체
	@GetMapping("user")
	public String userAll(Model model, UserVO vo) {
		List<UserVO> list = service.userAll();
		model.addAttribute("ulist",list);
		return "admember/user";
	}
	
	
}
