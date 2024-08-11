package com.talk.app.mypage.web;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talk.app.mypage.service.CoPostingService;
import com.talk.app.posting.service.PostingVO;

@Controller
public class CoPostingController {
	
	private static final Logger log = LoggerFactory.getLogger(CoPostingController.class);
	
	private CoPostingService copostingService;
	
	@Autowired
	public CoPostingController(CoPostingService copostingService) {
		this.copostingService = copostingService;
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
	public String copostingInfo(@RequestParam(value = "postingNo") Integer postingNo, Model model) {
        PostingVO postingVO = new PostingVO();
        postingVO.setPostingNo(postingNo);
        PostingVO findVO = copostingService.postingInfo(postingVO);
        
        model.addAttribute("posting", findVO);
        log.info("Posting No: {}", postingNo);

        return "mypage/copostingInfo";
    }
	
//	@GetMapping("copostingInfo")
//	public String copostingInfo(PostingVO postingVO, Model model) {
//        PostingVO findVO = new PostingVO();
//        model.addAttribute("copostingInfo", findVO);
//        log.info("기모찌 = ",findVO.getPostingStatus());
//        log.info("기모찌 = ",findVO.getPostingNo());
//
//        return "mypage/copostingInfo";
//    }
	
	// 마이페이지 채용공고 등록 - 페이지
//	@GetMapping("copostingInsert")
//	public String copostingInsertForm(Model model) {
//		return "mypage/copostingInsert";
//	}
	@GetMapping("copostingInsert")
    public String copostingInsertForm(Model model, Principal principal) {
        PostingVO postingVO = new PostingVO();
        String coUserId = principal.getName();
        // coUserNo를 설정하기 위해 서비스 호출
        int coUserNo = copostingService.getCoUserNoById(coUserId);
        postingVO.setCoUserNo(coUserNo);
        model.addAttribute("postingVO", postingVO);
        return "mypage/copostingInsert";
    }
	// 마이페이지 채용공고 등록 - 처리
//	@PostMapping("copostingInsert")
//	public String copostingInsertProcess(PostingVO postingVO) {
//		int pno = copostingService.insertPosting(postingVO);
//		
//		return "redirect:copostingInfo?postingNo=" + pno;
//	}
	@PostMapping("copostingInsert")
	public String copostingInsertProcess(PostingVO postingVO, Principal principal) {
	    // 현재 로그인한 사용자 ID를 가져와서 coUserNo를 설정합니다.
	    String coUserId = principal.getName();
	    int coUserNo = copostingService.getCoUserNoById(coUserId);
	    postingVO.setCoUserNo(coUserNo); // coUserNo 설정

	    int pno = copostingService.insertPosting(postingVO);

	    return "redirect:copostingInfo?postingNo=" + pno;
	}
}
