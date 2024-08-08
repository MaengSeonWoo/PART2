package com.talk.app.login.web;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.talk.app.login.service.LoginUserVO;
import com.talk.app.login.service.UserVO;

@Controller
public class LoginController {
	
	@GetMapping("/login") // 경로 앞에 /를 붙여 절대 경로로 지정
    public String login() {
        return "login/login"; // templates 폴더 아래의 login/login.html로 매핑
    }
	
	@GetMapping("/loginsel") // 경로 앞에 /를 붙여 절대 경로로 지정
    public String loginsel() {
        return "login/loginSelect"; // templates 폴더 아래의 login/login.html로 매핑
    }
	
	@PostMapping("/login_success_handler")
	   public String userAccess(HttpSession session, Authentication authentication) {
	      authentication = SecurityContextHolder.getContext().getAuthentication();
	      
	      // 유저 아이디 세션 등록
	      UserVO user = ((LoginUserVO) authentication.getPrincipal()).getUserVO();
	      
	      session.setAttribute("id", user.getUserId());
	      return "redirect:/";
	   }
}
