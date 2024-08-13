package com.talk.app.mypage.web;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.mypage.service.ResumeService;
import com.talk.app.mypage.service.ResumeVO;

import lombok.RequiredArgsConstructor;

/*
 * 작성자 : 김진형
 * 작성일자 : 2024-08-12
 * 이력서 관리 : 이력서 목록조회, 이력서상세조회/수정/삭제, 이력서등록
 * */

@Controller
@RequiredArgsConstructor
@RequestMapping("/userMain/resume")
//@PreAuthorize("isAuthenticated()")
public class ResumeController {

	private final ResumeService resumeService;
	
	@GetMapping
	public String resumeList(Principal principal, Model model)	 {
		String userId = principal.getName();
		List<ResumeVO> resumeList = resumeService.resumeList(userId);
		List<ResumeVO> applyResumeList = resumeService.applyResumeList(userId);
		model.addAttribute("rlist", resumeList);
		model.addAttribute("alist", applyResumeList);
		
		return "mypage/resumeList";
	}
	
	@GetMapping("/{resumeNo}")
	public String resumeInfo(Principal principal, Model model, @PathVariable int resumeNo) {
		String userId = principal.getName();
		if(resumeService.resumeInfo(resumeNo, userId) != null) {
			ResumeVO resumeInfo = resumeService.resumeInfo(resumeNo, userId);
			model.addAttribute("resume", resumeInfo);
			return "mypage/resumeInfo";
		} else {
			return "redirect:/userMain/resume";
		}
	}
	
	@GetMapping("/save")
	public String resumeSaveForm() {
		return "";
	}
	
	@ResponseBody
	@PostMapping("/save")
	public String resumeSave() {
		return "";
	}

	@GetMapping("/{resumeNo}/edit")
	public String resumeEditForm(Principal principal, Model model, @PathVariable int resumeNo) {
		String userId = principal.getName();
		if(resumeService.resumeInfo(resumeNo, userId) != null) {
			ResumeVO resumeInfo = resumeService.resumeInfo(resumeNo, userId);
			model.addAttribute("resume", resumeInfo);
			return "mypage/resumeUpdate";
		} else {
			return "redirect:/userMain/resume";
		}
	}
	
	@ResponseBody
	@PostMapping("/{resumeNo}/edit")
	public String resumeEdit(Principal principal, @RequestBody ResumeVO resumeVO) {
//		String userId = principal.getName();
		try {
            resumeService.editResume(resumeVO);
            return "success";  // 성공 시 "success" 반환
        } catch (Exception e) {
            return "error: " + e.getMessage();  // 실패 시 에러 메시지 반환
        }
	}
	
	
	
}









