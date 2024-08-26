package com.talk.app.home.web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.talk.app.common.service.Criteria;
import com.talk.app.common.service.PublicCodeService;
import com.talk.app.login.service.LoginUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.posting.service.PostingService;
import com.talk.app.posting.service.PostingVO;

import lombok.RequiredArgsConstructor;

/*
 * 작성자 : 김진형
 * 작성일자 : 2024-08-05
 * 메인페이지
 * */
@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final PostingService postingService;
	private final PublicCodeService publicCodeService;

	@GetMapping("/")
	public String mainPage(Model model, Criteria cri) {
		List<PostingVO> postingList = postingService.postingList(cri);
		model.addAttribute("pList", postingList);
		model.addAttribute("empTypeCode", publicCodeService.selectCode("0C"));
		
		// 현재 인증된 사용자의 권한 정보를 모델에 추가
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof LoginUserVO) {
            LoginUserVO loginUser = (LoginUserVO) auth.getPrincipal();
            UserVO user = loginUser.getUserVO();
            model.addAttribute("userAuthority", user.getAuthority()); // 사용자 권한을 모델에 추가
        }
		
		return "home/home";
	}
	
	
}
