package com.talk.app.notice.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.notice.service.NoticeService;
import com.talk.app.notice.service.NoticeVO;




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
	
	
	// 등록
	@GetMapping("noticeInsert")
	public String noticeInsertForm() {
		return "notice/noticeInsert";
	}
	
	// 등록 처리
	@PostMapping("noticeInsert")
	public String noticeInsertProcess(NoticeVO noticeVO) {
		int nno = noticeService.insertNotice(noticeVO);
		return "redircet:noticeInfo?noticeNo=" + nno;
	}
	
	// 수정
	@GetMapping("noticeUpdate")
	public String noticeUpdateForm(NoticeVO noticeVO, Model model) {
		NoticeVO findVO = noticeService.noticeInfo(noticeVO);
		model.addAttribute("noticeInfo", findVO);
		return "notice/noticeUpdate";
	}
	
	// 수정 처리
	@PostMapping("noticeUpdate")
	@ResponseBody
	public Map<String, Object> noticeUpdateProcess(@RequestBody NoticeVO noticeVO){
		return noticeService.updateNotice(noticeVO);
	}
	
	
	// 삭제
	@GetMapping("noticeDelete")
	public String noticeDelete(@RequestParam Integer noticeNo) {
		noticeService.deleteNotice(noticeNo);
		return "redirect:noticeList";
			
	}
	
}
