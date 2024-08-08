package com.talk.app.login.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	
}
