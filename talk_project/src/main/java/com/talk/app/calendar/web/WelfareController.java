package com.talk.app.calendar.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("welfarelist")
	public List<Map<String,Object>> welfaremain(Model model, Criteria cri, SearchVO vo) {
		 return service.calendar(vo);

	}

	@GetMapping("welfare")
	public String category(Model model,SearchVO vo, Criteria cri) {
		List<WelfareVO> clist = service.categoryData(vo);
        model.addAttribute("regionCode", pservice.selectCode("0G"));
        model.addAttribute("likeCode", pservice.selectCode("0L"));
		model.addAttribute("list",clist);
		model.addAttribute("page", new PageDTO(10,service.cntWelfare(vo),cri));
		return "calendar/list";
	}
	
	
//	  @GetMapping("list") public String json(Model model, Criteria cri){
//	  List<WelfareVO> list = service.selectCalendar(cri);
//	  model.addAttribute("clist",list);
//	  model.addAttribute("page", new PageDTO(10,service.cntWelfare(), cri)); 
//	  return "calendar/list"; }
//	 
	
	@GetMapping("detail")
	public String detail(Model model, WelfareVO vo) {
		WelfareVO findvo = service.welfareDetail(vo);
		model.addAttribute("detail",findvo);
		return "calendar/info";
	}
	
	
	
	
	
}
