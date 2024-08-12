package com.talk.app.sign.web;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.login.service.UserVO;
import com.talk.app.sign.service.SignService;

@Controller
public class SignController {
	private SignService signService;

	@Autowired
	public SignController(SignService signService) {
		this.signService = signService;
	}
	
	@GetMapping("/signsel")
	public String sign() {
		return "sign/signSelect";
	}
	
	@GetMapping("/signInsert")
	public String signInsertForm() {
		return "sign/signInsert";
	}
	
	@PostMapping("/signInsert")
	public String signInsertProcess(UserVO userVO) {
		signService.insertSign(userVO);
		return "redirect:login";
	}
	
	@PostMapping("/checkUserId")
	@ResponseBody
	public Map<String, String> process(@RequestParam String userId) {
		Map<String, String> mapAjax = new HashMap<>();

		UserVO userVO = signService.selectCheckUser(userId);

		if (userVO != null) {
			mapAjax.put("result", "idDuplicated");
		} else {
				mapAjax.put("result", "idNotFound");
		}
		return mapAjax;
	}
}
