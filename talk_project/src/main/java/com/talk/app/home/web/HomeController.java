package com.talk.app.home.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.common.service.Criteria;
import com.talk.app.common.service.PageDTO;
import com.talk.app.posting.service.PostingService;
import com.talk.app.posting.service.PostingVO;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final PostingService postingService;

	@GetMapping("/")
	public String mainPage(Model model, Criteria cri) {
		List<PostingVO> postingList = postingService.postingList(cri);
		model.addAttribute("pList", postingList);
		return "home/home";
	}
	
	
}
