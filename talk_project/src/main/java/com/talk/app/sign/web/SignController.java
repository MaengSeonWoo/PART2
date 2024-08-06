package com.talk.app.sign.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.talk.app.login.service.UserVO;
import com.talk.app.sign.service.SignService;


@Controller
public class SignController {
	private SignService signService;
	
	@GetMapping("/signsel") // 경로 앞에 /를 붙여 절대 경로로 지정
    public String sign() {
        return "sign/signSelect"; // templates 폴더 아래의 login/login.html로 매핑
    }
	
	@Autowired
	public SignController(SignService signService) {
		this.signService = signService;
	}
	
	@GetMapping("signInsert")
	public String signInsertForm(Model model) {
		return "sign/signInsert";
	}
	
	@PostMapping("signInsert")
	public String signInsertProcess(UserVO userVO) {
		int uno = signService.insertSign(userVO);
		return "redirect:login";
	}
}
