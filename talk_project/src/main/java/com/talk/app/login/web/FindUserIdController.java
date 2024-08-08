package com.talk.app.login.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talk.app.login.service.FindUserIdService;
import com.talk.app.login.service.UserVO;

@Controller
public class FindUserIdController {
	
	@Autowired
	FindUserIdService findIdService;
	
	@GetMapping("findId")
	public String findId(UserVO userVO, Model model) {
		String findVO = findIdService.findUserId(userVO);
		model.addAttribute("findId", findVO);
		return "login/findid";
	}
	
	@PostMapping("/findIdResult")
    public String findId(@RequestParam String userName, @RequestParam String Tel, Model model) {
        UserVO userVO = new UserVO();
        userVO.setUserName(userName);
        userVO.setTel(Tel);

        String userId = findIdService.findUserId(userVO);
        model.addAttribute("userId", userId);

        return "login/findidresult";  // 아이디 결과를 보여줄 페이지
    }
}
