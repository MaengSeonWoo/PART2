package com.talk.app.mypage.web;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.talk.app.mypage.service.ResumeService;
import com.talk.app.mypage.service.ResumeVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/userMain/resume")
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
	
}
