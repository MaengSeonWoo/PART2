// CustomAuthenticationSuccessHandler.java
package com.talk.app.login.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.mypage.service.CoUserUpdateService;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final CoUserUpdateService coUserUpdateService;

    // 생성자 주입
    public CustomAuthenticationSuccessHandler(CoUserUpdateService coUserUpdateService) {
        this.coUserUpdateService = coUserUpdateService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        CoUserVO coUserVO = new CoUserVO();
        coUserVO.setCoUserId(username);

        CoUserVO user = coUserUpdateService.couserInfo(coUserVO);
        if (user.getDelStatus() == 0) {
        	response.sendRedirect(request.getContextPath() + "/"); // 
        } else if (user.getDelStatus() == 1){
        	response.sendRedirect(request.getContextPath() + "/cancelDel"); // 
        } 
    }
}
