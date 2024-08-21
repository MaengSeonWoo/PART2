package com.talk.app.login.web;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.talk.app.login.service.LoginUserVO;
import com.talk.app.login.service.UserVO;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login() {
        return "login/login";
    }
    
    @GetMapping("/loginsel")
    public String loginsel() {
        return "login/loginSelect";
    }
    
    @PostMapping("/login_success_handler")
    public String userAccess(HttpSession session, Authentication authentication, Model model) {
        // 현재 Authentication 객체 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // Authentication 객체가 null이거나 Principal이 올바르지 않으면
        if (auth == null || !(auth.getPrincipal() instanceof LoginUserVO)) {
            // 로그인 페이지로 리디렉션하며 에러 메시지 전달
            return "redirect:/login";
        }
        
        // Principal에서 UserVO 정보 추출
        LoginUserVO loginUser = (LoginUserVO) auth.getPrincipal();
        UserVO user = loginUser.getUserVO();
        
        // 세션에 사용자 ID 저장
        session.setAttribute("id", user.getUserId());
        
        // 세션 유지 시간 설정
        session.setMaxInactiveInterval(3600);
        
        String userAuthority = (String) session.getAttribute("ROLE");
        model.addAttribute("userAuthority", userAuthority); // 로그인한 유저의 권한을 가져옴
        model.addAttribute("userId", user.getUserId());
        
        return "redirect:/";
    }
    
}
