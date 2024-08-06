package com.talk.app.login.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoLoginController {
	@GetMapping("/cologin")
	public String cologin() {
		return "/login/cologin";
	}
}
