package com.talk.app.posting.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.talk.app.common.service.Criteria;
import com.talk.app.common.service.PageDTO;
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
	
	@GetMapping
	public String postingList(Model model, Criteria cri) {
		// 기능 수행
		List<PostingVO> postingList = postingService.postingList(cri);
		// 채용공고 리스트 전달
		model.addAttribute("pList", postingList);
		// 채용공고 페이징 처리를 위한 데이터 전달
		model.addAttribute("page", new PageDTO(10, postingService.getTotal(), cri));
		// 출력 페이지
		return "posting/list";
	}
	@GetMapping("/{postingNo}")
	public String postingInfo(HttpSession session, Model model, @PathVariable Integer postingNo) {
		// 기능 수행
		PostingVO postingInfo = postingService.postingInfo(postingNo);
		// 채용공고 리스트 전달
		model.addAttribute("posting", postingInfo);
		model.addAttribute("login", session.getAttribute("id"));		
		// 출력 페이지
		return "posting/info";
	}
}
