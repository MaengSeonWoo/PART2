package com.talk.app.calendar.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.calendar.service.CalendarService;
import com.talk.app.common.service.Criteria;
import com.talk.app.common.service.PageDTO;
import com.talk.app.common.service.PublicCodeService;
import com.talk.app.common.service.SearchVO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/calendar")
@RequiredArgsConstructor
@Controller
public class WelfareController {
	
	@Autowired
	CalendarService service;
	
	@Autowired
	PublicCodeService pservice;

	@ResponseBody
	@GetMapping("calendar")
	public List<Map<String, Object>> calendar(SearchVO vo){
	 return service.calendar(vo);
	}
	
	@GetMapping("welfare")
	public String category(Model model,SearchVO vo, Criteria cri) {
	    // 리스트 데이터
		List<WelfareVO> clist = service.categoryData(vo);
        model.addAttribute("regionCode", pservice.selectCode("0G"));
        model.addAttribute("likeCode", pservice.selectCode("0L"));
		model.addAttribute("list",clist);
		model.addAttribute("page", new PageDTO(10,service.cntWelfare(vo),cri));
		return "calendar/list";
	}
	
	@GetMapping("/updateMonth")
	@ResponseBody
	public List<WelfareVO> updateMonthData(@RequestParam("month") String month, SearchVO vo) {
	    vo.setMonth(month); 
	    return service.categoryData(vo); 
	}
	
	@GetMapping("detail")
	public String detail(Model model, WelfareVO vo) {
		WelfareVO findvo = service.welfareDetail(vo);
		model.addAttribute("detail",findvo);
		return "calendar/info";
	}
	
	
	
	
	
}
