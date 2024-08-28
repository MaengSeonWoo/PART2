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
	
	@PostMapping("/findPwResult")
    public String findTemporaryPassword(@RequestParam String userName,
                                        @RequestParam String userId,
                                        @RequestParam String userEmail,
                                        Model model) {
        UserVO userVO = new UserVO();
        userVO.setUserName(userName);
        userVO.setUserId(userId);
        userVO.setEmail(userEmail);

        boolean result = findIdService.sendTempPw(userVO);
        if (result) {
            model.addAttribute("message", "임시 비밀번호가 이메일로 전송되었습니다.");
        } else {
            model.addAttribute("message", "임시 비밀번호 전송에 실패하였습니다.");
        }

        return "login/findPwResult"; // 결과 페이지로 리다이렉션
    }
}
