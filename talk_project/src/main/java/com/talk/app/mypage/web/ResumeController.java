package com.talk.app.mypage.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talk.app.common.service.UploadService;
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
	private final UploadService uploadService;
	
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
	
//	@ResponseBody
//	@PostMapping("/{resumeNo}/edit")
//	public String resumeEdit(Principal principal, @PathVariable("resumeNo") int resuneNo, @RequestBody ResumeVO resumeVO) {
////		String userId = principal.getName();
//		try {
//            resumeService.editResume(resumeVO);
//            return "success";  // 성공 시 "success" 반환
//        } catch (Exception e) {
//            return "error: " + e.getMessage();  // 실패 시 에러 메시지 반환
//        }
//	}
	@PostMapping("/{resumeNo}/edit")
	@ResponseBody
	public Map<String, Object> updateResume(Principal principal, @PathVariable("resumeNo") int resumeNo, @RequestPart("resumeData") String resumeData, @RequestPart("uploadFiles") MultipartFile[] uploadFiles) {
	    Map<String, Object> result = new HashMap<>();

	    try {
		    // JSON 데이터를 ResumeVO 객체로 변환
	        ObjectMapper objectMapper = new ObjectMapper();
	        ResumeVO resume = objectMapper.readValue(resumeData, ResumeVO.class);
	        // 이력서 업데이트 로직 수행
	        resumeService.editResume(resume);
	        
	        // 파일 업데이트 로직 수행
	        uploadService.imageUpdate(uploadFiles, "RESUME", (long)resumeNo);

	        // 성공 응답 설정
	        result.put("success", true);
	    } catch (Exception e) {
	        // 오류 발생 시 응답 설정
	        result.put("success", false);
	        result.put("message", "이력서 업데이트 중 오류가 발생했습니다.");
	        e.printStackTrace(); // 오류 로깅
	    }

	    return result;
	}

	
	
}









