package com.talk.app.sign.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.common.service.PublicCodeService;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
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
	
	@RequestMapping("/checkCoUserId")
	@ResponseBody
	public Map<String, String> process(@RequestParam String CoUserId) {
		Map<String, String> mapAjax = new HashMap<>();

		CoUserVO couserVO = cosignService.selectCheckCoUser(CoUserId);

		if (couserVO != null) {
			mapAjax.put("result", "idDuplicated");
		} else {
				mapAjax.put("result", "idNotFound");
		}
		return mapAjax;
	}
}
