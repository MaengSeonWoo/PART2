package com.talk.app.mypage.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talk.app.common.service.CommonUtil;
import com.talk.app.common.service.LoginException;
import com.talk.app.common.service.PublicCodeService;
import com.talk.app.common.service.UploadService;
import com.talk.app.mypage.service.CoPostingService;
import com.talk.app.notice.service.NoticeService;
import com.talk.app.posting.service.PostingVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CoPostingController {
	
	private static final Logger log = LoggerFactory.getLogger(CoPostingController.class);
	
	private CoPostingService copostingService;
	private PublicCodeService publiccodeService;
	private UploadService uploadService;
	
	@Autowired
	public CoPostingController(CoPostingService copostingService, PublicCodeService publiccodeService, UploadService uploadService) {
		this.copostingService = copostingService;
		this.publiccodeService = publiccodeService;
		this.uploadService = uploadService;
	}
	
	// 마이페이지 채용공고 전체조회
	@GetMapping("copostingList")
    public String copostingList(Model model, Principal principal) {
        String coUserId = principal.getName();
        
        // 사용자 ID를 전달하여 채용 공고 목록을 가져옵니다.
        List<PostingVO> list = copostingService.postingList(coUserId);
        model.addAttribute("copostingList", list);
        
        return "mypage/copostingList";
    }
	
	// 마이페이지 채용공고 단건조회
	@GetMapping("copostingInfo")
	public String copostingInfo( PostingVO postingVO , Model model) {
        PostingVO findVO = copostingService.postingInfo(postingVO);
        
        model.addAttribute("posting", findVO);
        log.info("Posting No: {}", postingVO.getPostingNo());

        return "mypage/copostingInfo";
    }
	@GetMapping("copostingInsert")
    public String copostingInsertForm(Model model, Principal principal) {
        PostingVO postingVO = new PostingVO();
        String coUserId = principal.getName();
        // coUserNo를 설정하기 위해 서비스 호출
        int coUserNo = copostingService.getCoUserNoById(coUserId);
        postingVO.setCoUserNo(coUserNo);
        model.addAttribute("postingVO", postingVO);
        model.addAttribute("regionCode", publiccodeService.selectCode("0G")); // codeRule이 0G인 지역 코드를 조회하고, 이를 모델에 담아 화면에 전달
        return "mypage/copostingInsert";
    }
	@PostMapping("copostingInsert")
	public String copostingInsertProcess(PostingVO postingVO, HttpServletResponse response) throws LoginException {
	    // 현재 로그인한 사용자 ID를 가져와서 coUserNo를 설정합니다.
	    String coUserId = CommonUtil.getUserId();
	    int coUserNo = copostingService.getCoUserNoById(coUserId);
	    
	    postingVO.setCoUserNo(coUserNo); // coUserNo 설정
	    
	    int pno;
	    pno = copostingService.insertPosting(postingVO);
	    
    	return "redirect:copostingInfo?postingNo=" + pno;
	}
	// 채용공고 수정페이지
	@GetMapping("copostingUpdate")
	public String copostingUpdateForm(PostingVO postingVO, Model model) {
		PostingVO findVO = copostingService.postingInfo(postingVO);
		model.addAttribute("copostingInfo", findVO);
		model.addAttribute("regionCode", publiccodeService.selectCode("0G"));
		model.addAttribute("empTypeCode", publiccodeService.selectCode("0C"));
		model.addAttribute("genderCode", publiccodeService.selectCode("0E"));
		
		return "mypage/copostingUpdate";
	}
	
	// 채용공고 수정처리
	@PostMapping("copostingUpdate")
	@ResponseBody
	public Map<String, Object> copostingUpdateAJAXJSON(@RequestBody PostingVO postingVO){
		return copostingService.updatePosting(postingVO);
	}
	
	@GetMapping("copostingDelete")
	public String copostingDelete(@RequestParam Integer postingNo) {
		copostingService.deletePosting(postingNo);
		
		return "redirect:copostingList";
	}
}
