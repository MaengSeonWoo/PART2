package com.talk.app.posting.web;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.talk.app.common.service.Criteria;
import com.talk.app.common.service.PageDTO;
import com.talk.app.common.service.UploadFileVO;
import com.talk.app.common.service.UploadService;
import com.talk.app.login.service.LoginUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.mypage.service.ResumeService;
import com.talk.app.mypage.service.ResumeVO;
import com.talk.app.posting.service.PostingService;
import com.talk.app.posting.service.PostingVO;

import lombok.RequiredArgsConstructor;

/*
 * 작성자 : 김진형
 * 작성일자 : 2024-08-06
 * 채용공고 조회 : 채용공고 리스트, 채용공고 상세조회
 * */
@Controller
@RequestMapping("/posting")
@RequiredArgsConstructor
public class PostingController {
	
	private final PostingService postingService;
	private final ResumeService resumeService;
	private final UploadService uploadService;
	
	@GetMapping
	public String postingList(Model model, Criteria cri) {
		// 기능 수행
		List<PostingVO> postingList = postingService.postingList(cri);
		// 채용공고 리스트 전달
		model.addAttribute("pList", postingList);
		// 채용공고 페이징 처리를 위한 데이터 전달
		model.addAttribute("page", new PageDTO(10, postingService.getTotal(), cri));
        
		// 현재 인증된 사용자의 권한 정보를 모델에 추가
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof LoginUserVO) {
            LoginUserVO loginUser = (LoginUserVO) auth.getPrincipal();
            UserVO user = loginUser.getUserVO();
            model.addAttribute("userAuthority", user.getAuthority()); // 사용자 권한을 모델에 추가
        }
		
		// 출력 페이지
		return "posting/list";
	}
	@GetMapping("/{postingNo}")
	public String postingInfo(Principal principal, Model model, @PathVariable Integer postingNo) {
		String userId = principal.getName();
		ResumeVO resume = new ResumeVO();
		resume.setUserId(userId);
		// 기능 수행
		PostingVO postingInfo = postingService.postingInfo(postingNo);
		List<ResumeVO> resumeList = resumeService.resumeList(resume);
		
		// 채용공고 리스트 전달
		model.addAttribute("posting", postingInfo);
		model.addAttribute("rList", resumeList);
		
		// 현재 인증된 사용자의 권한 정보를 모델에 추가
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof LoginUserVO) {
            LoginUserVO loginUser = (LoginUserVO) auth.getPrincipal();
            UserVO user = loginUser.getUserVO();
            model.addAttribute("userAuthority", user.getAuthority()); // 사용자 권한을 모델에 추가
        }
		
		// 출력 페이지
		return "posting/info";
	}
	@GetMapping("/{postingNo}/resume/{resumeNo}")
	public String myResumeInfo(Principal principal, Model model, @PathVariable Integer postingNo, @PathVariable Integer resumeNo) {
		String userId = principal.getName();
//		ResumeVO resume = new ResumeVO();
//		resume.setUserId(userId);
		
		// 기능 수행
		if(resumeService.resumeInfo(resumeNo, userId) != null) {
			ResumeVO resumeInfo = resumeService.resumeInfo(resumeNo, userId);
			List<UploadFileVO> images = uploadService.selectFilesByDomain("RESUME", (long) resumeNo);
			if(!images.isEmpty()) {
				model.addAttribute("profile", images.get(0));
			}
		
		// 채용공고 리스트 전달
		model.addAttribute("resume", resumeInfo);
		return "posting/resumeInfo";
		} else {
			return "redirect:/userMain/posting/"+postingNo;
		}
		// 출력 페이지
	}
}
