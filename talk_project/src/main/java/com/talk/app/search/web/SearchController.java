package com.talk.app.search.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.search.service.SearchService;
import com.talk.app.search.service.mainSearchVO;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class SearchController {

	private final SearchService searchService;
	
	@ResponseBody
	@GetMapping("/search")
	public List<mainSearchVO> searchMain(mainSearchVO searchVO, Model model) {
		 return searchService.searchAll(searchVO);
	}
	
	
}
