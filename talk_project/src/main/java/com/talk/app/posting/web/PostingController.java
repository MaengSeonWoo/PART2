package com.talk.app.posting.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.talk.app.posting.service.PostingService;
import com.talk.app.posting.service.PostingVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/posting")
@RequiredArgsConstructor
public class PostingController {
	
	private final PostingService postingService;
	
	@GetMapping
	public String postingList(Model model) {
		// 기능 수행
		List<PostingVO> postingList = postingService.postingList();
		// 채용공고 리스트 전달
		model.addAttribute("pList", postingList);
		// 출력 페이지
		return "posting/list";
	}
}
