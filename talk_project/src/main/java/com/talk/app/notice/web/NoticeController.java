package com.talk.app.notice.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.talk.app.notice.service.NoticeService;
import com.talk.app.notice.service.NoticeVO;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class NoticeController {

	private NoticeService noticeService;
	
	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	// 전체조회
	@GetMapping("noticeList")
	public String NoticeList(Model model) {
		List<NoticeVO> list = noticeService.noticeList();
		model.addAttribute("noticeList", list);
		
		return "notice/noticeList";
	}
	
	// 단건조회
	@GetMapping("noticeInfo")
	public String noticeInfo(NoticeVO noticeVO, Model model) {
		NoticeVO findVO = noticeService.noticeInfo(noticeVO);
		model.addAttribute("noticeInfo", findVO);
		return "notice/noticeInfo";
	}
	
}
