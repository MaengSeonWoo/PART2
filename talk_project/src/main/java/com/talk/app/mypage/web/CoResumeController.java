package com.talk.app.mypage.web;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.talk.app.common.service.PublicCodeService;
import com.talk.app.common.service.UploadFileVO;
import com.talk.app.common.service.UploadService;
import com.talk.app.mypage.service.CoResumeService;
import com.talk.app.mypage.service.ResumeVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CoResumeController {

	private final CoResumeService resumeService;
	private final UploadService uploadService;
	private final PublicCodeService publiccodeService;

	@GetMapping("coresumelist")
	public String resumeList(Principal principal, Model model) {
		String userId = principal.getName();
		List<ResumeVO> coResumeList = resumeService.coResumeList(userId);
		model.addAttribute("coresume", coResumeList);
		model.addAttribute("resultCode", publiccodeService.selectCode("0D"));

		return "mypage/coResumeList";
	}

	@GetMapping("coresumeinfo/{resumeNo}")
	public String coResumeInfo(@PathVariable int resumeNo, Model model) {
	    ResumeVO findVO = resumeService.coResumeInfo(resumeNo);

	    List<UploadFileVO> images = uploadService.selectFilesByDomain("RESUME", (long) resumeNo);
	    if (!images.isEmpty()) {
	        model.addAttribute("profile", images.get(0));
	    }
	    model.addAttribute("resume", findVO);
	    return "mypage/coResumeInfo";
	}
}
