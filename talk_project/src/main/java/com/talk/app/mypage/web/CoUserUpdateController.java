package com.talk.app.mypage.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.mypage.service.CoUserUpdateService;

@Controller
public class CoUserUpdateController {
	
	private CoUserUpdateService couserupdateService;
	
	@Autowired
	public CoUserUpdateController(CoUserUpdateService couserupdateService) {
		this.couserupdateService = couserupdateService;
	}
	
	// 기업회원 수정 페이지
	@GetMapping("CoUserUpdate")
	public String CoUserUpdateForm(CoUserVO couserVO, Model model) {
		CoUserVO findVO = couserupdateService.couserInfo(couserVO);
		model.addAttribute("couserInfo", findVO);
		
		return "mypage/couserUpdate";
	}
	
	// 기업회원 수정 처리
	@PostMapping("CoUserUpdate")
	@ResponseBody
	public Map<String, Object> couserUpdateAJAXJSON(@RequestBody CoUserVO couserVO){
		return couserupdateService.updateCoUser(couserVO);
	}
}
