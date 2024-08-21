package com.talk.app.posting.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.common.service.Criteria;
import com.talk.app.common.service.PageDTO;
import com.talk.app.common.service.PublicCodeService;
import com.talk.app.common.service.UploadFileVO;
import com.talk.app.common.service.UploadService;
import com.talk.app.login.service.LoginUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.mypage.service.ResumeService;
import com.talk.app.mypage.service.ResumeVO;
import com.talk.app.posting.service.PostingService;
import com.talk.app.posting.service.PostingVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * 작성자 : 김진형
 * 작성일자 : 2024-08-06
 * 채용공고 조회 : 채용공고 리스트, 채용공고 상세조회
 * */
@Slf4j
@Controller
@RequestMapping("/posting")
@RequiredArgsConstructor
public class PostingController {
	
	private final PostingService postingService;
	private final ResumeService resumeService;
	private final UploadService uploadService;
	private final PublicCodeService publicCodeService;
	
//	@GetMapping
//	public String postingList(Model model, Criteria cri) {
//		// 기능 수행
//		List<PostingVO> postingList = postingService.postingList(cri);
//		// 채용공고 리스트 전달
//		model.addAttribute("pList", postingList);
//		// 채용공고 페이징 처리를 위한 데이터 전달
//		model.addAttribute("page", new PageDTO(10, postingService.getTotal(), cri));
//		// 출력 페이지
//		return "posting/list";
//	}
	@GetMapping

	public String postingList(Criteria cri, Model model, HttpServletRequest request) {
		// 채용 공고 목록 조회
	    List<PostingVO> postingList = postingService.postingList(cri);
	    
	    
	    // 모델에 조회 결과 전달
	    model.addAttribute("pList", postingList);
	    model.addAttribute("regionCode", publicCodeService.selectCode("0G"));
	    model.addAttribute("empTypeCode", publicCodeService.selectCode("0C"));
	    // 페이징 처리를 위한 데이터 전달
	    int total = postingService.getTotal(cri);
	    model.addAttribute("page", new PageDTO(10, total, cri));

	    // 반환할 뷰 이름
	    return "posting/list";
	}
	
	@GetMapping("/{postingNo}")
	public String postingInfo(Model model, @PathVariable Integer postingNo, HttpSession session) {
		String userId = (String)session.getAttribute("userId");
		log.info("userId={}", userId);
		ResumeVO resume = new ResumeVO();
		resume.setUserId(userId);
		// 기능 수행
		PostingVO postingInfo = postingService.postingInfo(postingNo);
		List<ResumeVO> resumeList = resumeService.resumeList(resume);
		model.addAttribute("rpage", new PageDTO(8, resumeService.getResumeTotal(userId), resume));
		
		
		model.addAttribute("regionCode", publicCodeService.selectCode("0G"));
		model.addAttribute("empTypeCode", publicCodeService.selectCode("0C"));
		model.addAttribute("postingCode", publicCodeService.selectCode("0M"));
		model.addAttribute("genderCode", publicCodeService.selectCode("0E"));
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
	
	@ResponseBody
	@PostMapping("/{postingNo}/resume/{resumeNo}")
	public Map<String, Object> applyResume(Model model, @PathVariable Integer postingNo, @PathVariable Integer resumeNo) {
		ResumeVO resume = new ResumeVO();
		resume.setPostingNo(postingNo);
		resume.setResumeNo(resumeNo);
		
		Map<String, Object> applyResume = postingService.applyResume(resume);
		
		return applyResume;
	}
}
