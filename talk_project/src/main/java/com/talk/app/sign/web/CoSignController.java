package com.talk.app.sign.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.sign.service.CoSignService;

@Controller
public class CoSignController {
	private CoSignService cosignService;
	
	@Autowired
	public CoSignController(CoSignService cosignService) {
		this.cosignService = cosignService; 
	}
	
	@GetMapping("cosignInsert")
	public String cosignInsertForm(Model model) {
		return "sign/cosignInsert";
	}
	
	@PostMapping("cosignInsert")
	public String cosignInsertProcess(CoUserVO couserVO) {
		int uno = cosignService.insertCoSign(couserVO);
		return "redirect:login";
	}
}
