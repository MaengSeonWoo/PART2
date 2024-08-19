package com.talk.app.mypage.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talk.app.common.service.PageDTO;
import com.talk.app.common.service.UploadFileVO;
import com.talk.app.common.service.UploadService;
import com.talk.app.mypage.service.ResumeService;
import com.talk.app.mypage.service.ResumeVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * 작성자 : 김진형
 * 작성일자 : 2024-08-12
 * 이력서 관리 : 이력서 목록조회, 이력서상세조회/수정/삭제, 이력서등록
 * */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/userMain/resume")
//@PreAuthorize("isAuthenticated()")
public class ResumeController {

	private final ResumeService resumeService;
	private final UploadService uploadService;
	
	@GetMapping
	public String resumeList(Principal principal, 
							Model model, 
							ResumeVO resume1,
							ResumeVO resume2,
							@RequestParam(defaultValue = "1", required = false) int pageNum1, 
							@RequestParam(defaultValue = "1", required = false) int pageNum2)	 {
		String userId = principal.getName();
		
		// 지원이력서
		resume1.setUserId(userId);
		resume1.setPageNum(pageNum1);
		System.out.println("dddddddd" + resume1.getPageNum());
		List<ResumeVO> applyResumeList = resumeService.applyResumeList(resume1);
		model.addAttribute("alist", applyResumeList);
		model.addAttribute("arpage", new PageDTO(8, resumeService.getApplyResumeTotal(userId), resume1));
		
		// 나의 이력서		
		resume2.setUserId(userId);
		resume2.setPageNum(pageNum2);		
		List<ResumeVO> resumeList = resumeService.resumeList(resume2);
		model.addAttribute("rlist", resumeList);		
		model.addAttribute("rpage", new PageDTO(8, resumeService.getResumeTotal(userId), resume2));
//		System.out.println("ddddd" + pageNum1);
		
		return "mypage/resumeList";
	}
	
	@GetMapping("/{resumeNo}")
	public String resumeInfo(Principal principal, Model model, @PathVariable int resumeNo) {
		String userId = principal.getName();
		if(resumeService.resumeInfo(resumeNo, userId) != null) {
			ResumeVO resumeInfo = resumeService.resumeInfo(resumeNo, userId);
			List<UploadFileVO> images = uploadService.selectFilesByDomain("RESUME", (long) resumeNo);
			if(!images.isEmpty()) {
				model.addAttribute("profile", images.get(0));
			}
			model.addAttribute("resume", resumeInfo);
			return "mypage/resumeInfo";
		} else {
			return "redirect:/userMain/resume";
		}
	}
	
	@GetMapping("/save")
	public String resumeSaveForm() {
		return "mypage/resumeInsert";
	}
	
	@ResponseBody
	@PostMapping("/save")
	public ResponseEntity<?> resumeSave(Principal principal, @RequestParam("resumeData") String resumeData, @RequestPart("uploadFiles") MultipartFile[] uploadFiles) {
		String userId = principal.getName();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
            ResumeVO resumeVO = objectMapper.readValue(resumeData, ResumeVO.class);
            resumeService.saveResume(resumeVO, uploadFiles, userId);
            return ResponseEntity.ok().body(Map.of("success", true));
        } catch (Exception e) {
            e.printStackTrace();
            log.info("exception={}", e);
            return ResponseEntity.ok().body(Map.of("success", false));
        }
	}

	@GetMapping("/{resumeNo}/edit")
	public String resumeEditForm(Principal principal, Model model, @PathVariable int resumeNo) {
		String userId = principal.getName();
		if(resumeService.resumeInfo(resumeNo, userId) != null) {
			ResumeVO resumeInfo = resumeService.resumeInfo(resumeNo, userId);
			model.addAttribute("resume", resumeInfo);
			List<UploadFileVO> images = uploadService.selectFilesByDomain("RESUME", (long) resumeNo);
			if(!images.isEmpty()) {
				model.addAttribute("profile", images.get(0));
			}
			return "mypage/resumeUpdate";
		} else {
			return "redirect:/userMain/resume";
		}
	}

	@PostMapping("/{resumeNo}/edit")
	@ResponseBody
	public Map<String, Object> updateResume(Principal principal, @PathVariable("resumeNo") int resumeNo, @RequestPart("resumeData") String resumeData, @RequestPart("uploadFiles") MultipartFile[] uploadFiles) {
	    Map<String, Object> result = new HashMap<>();

	    try {
		    // JSON 데이터를 ResumeVO 객체로 변환
	        ObjectMapper objectMapper = new ObjectMapper();
	        ResumeVO resume = objectMapper.readValue(resumeData, ResumeVO.class);
	        // 이력서 업데이트 로직 수행
	        
	        // 파일 업데이트 로직 수행
	        String saveName = uploadService.imageUpdate(uploadFiles, "RESUME", (long)resumeNo);
	        resume.setResumeImg(saveName);
	        resumeService.editResume(resume);

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
	
	@PostMapping("{resumeNo}/remove")
	public String deleteResume(@PathVariable Integer resumeNo) {
		resumeService.removeResume(resumeNo);
		return "redirect:/userMain/resume";
	}
	
}









