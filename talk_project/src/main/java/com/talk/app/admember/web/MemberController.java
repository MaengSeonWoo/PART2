package com.talk.app.admember.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.admember.service.MemberService;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

@Controller
@RequestMapping("admin/approve")
public class MemberController {

	@Autowired
	MemberService service;
	
	//기업회원 승인목록(기업목록 전체 신청desc처리)
	@GetMapping("")
	public String approveAll(Model model, CoUserVO vol) {
		List<CoUserVO> list = service.approveAll();
		model.addAttribute("list",list);
		return "admember/approvelist";
	}
	
	//기업회원 가입신청 상세
	@GetMapping("detail")
	public String approveDetail(Model model, CoUserVO vo) {
		CoUserVO findVO = service.coDetail(vo);
		model.addAttribute("detail", findVO);
		return "admember/coDetail";
	}
	
	//기업회원 승인 => 
	@GetMapping("confirm")
	public String updateCo(int coUserNo) {
		int wid = service.coUpdate(coUserNo);
		return "redirect:detail?coUserNo="+ wid;
		
	}
	
	
	
	//기업회원 거절
	
	
	
	
	
	
	
	//일반회원전체
	@GetMapping("user")
	public String userAll(Model model, UserVO vo) {
		List<UserVO> list = service.userAll();
		model.addAttribute("ulist",list);
		return "admember/user";
	}
	
	
}
