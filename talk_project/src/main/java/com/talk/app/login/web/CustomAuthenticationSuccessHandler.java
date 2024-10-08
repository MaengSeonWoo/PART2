package com.talk.app.login.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
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
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            String role = authentication.getAuthorities().toString();

            // 세션에 userId 추가
            request.getSession().setAttribute("userId", username);

            // 권한에 따라 ROLE 정보 세션에 저장
            if (role.contains("ROLE_USER")) {
                request.getSession().setAttribute("ROLE", "user");
            } else if (role.contains("ROLE_ADMIN")) {
                request.getSession().setAttribute("ROLE", "admin");
            } else {
                request.getSession().setAttribute("ROLE", "co_user");
            }

            // 권한에 따라 페이지 리다이렉트
            if (role.contains("ROLE_ADMIN")) {
                response.sendRedirect(request.getContextPath() + "/admin");
                return;
            } else if (role.contains("ROLE_USER")) {
                UserVO userVO = new UserVO();
                userVO.setUserId(username);
                UserVO user = coUserUpdateService.userInfo(userVO);
                if (user != null) {
                    if (user.getDelStatus() == 0) {
                        response.sendRedirect(request.getContextPath() + "/");
                    } else if (user.getDelStatus() == 1) {
                        response.sendRedirect(request.getContextPath() + "/canceluserdel");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/login?error");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login?error");
                }
                return;
            } else {
                CoUserVO coUserVO = new CoUserVO();
                coUserVO.setCoUserId(username);
                CoUserVO coUser = coUserUpdateService.couserInfo(coUserVO);
                if (coUser != null) {
                    if (coUser.getDelStatus() == 0) {
                        response.sendRedirect(request.getContextPath() + "/");
                    } else if (coUser.getDelStatus() == 1) {
                        response.sendRedirect(request.getContextPath() + "/cancelDel");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/login?error");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login?error");
                }
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/login?error");
    }
}
