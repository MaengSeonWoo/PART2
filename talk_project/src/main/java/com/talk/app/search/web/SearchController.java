package com.talk.app.search.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.talk.app.search.service.SearchService;
import com.talk.app.search.service.mainSearchVO;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class SearchController {

	private final SearchService searchService;
	
	
	@GetMapping("/search")
	public String searchMain(mainSearchVO searchVO, Model model) {
		List<mainSearchVO> searchResult = searchService.searchAll(searchVO);
		model.addAttribute("result", searchResult);
		
		return "common.main/fragments/header";
	}
	
	
}
