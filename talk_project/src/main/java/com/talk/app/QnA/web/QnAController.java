package com.talk.app.QnA.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.talk.app.QnA.service.QnAService;
import com.talk.app.QnA.service.QnAVO;

@Controller
public class QnAController {
	
	private QnAService qnaService;
	
	public QnAController(QnAService qnaService) {
		this.qnaService = qnaService;
	}
	
	// 전체조회
	@GetMapping("qnaList")
	public String qnaList(Model model) {
		List<QnAVO> list = qnaService.qnaList();
		model.addAttribute("qnaList", list);
		
		return "qna/qnaList";
	}
	
	// 단건조회
	@GetMapping("qnaInfo")
	public String qnaInfo(QnAVO qnaVO, Model model) {
		QnAVO findVO = qnaService.qnaInfo(qnaVO);
		model.addAttribute("qnaInfo", findVO);
		return "qna/qnaInfo";
	}
	
	
//	// 등록
//	@GetMapping("noticeInsert")
//	public String noticeInsertForm() {
//		return "notice/noticeInsert";
//	}
//	
//	// 등록 처리
//	@PostMapping("noticeInsert")
//	public String noticeInsertProcess(NoticeVO noticeVO) {
//		int nno = noticeService.insertNotice(noticeVO);
//		return "redircet:noticeInfo?noticeNo=" + nno;
//	}
//	
//	// 수정
//	@GetMapping("noticeUpdate")
//	public String noticeUpdateForm(NoticeVO noticeVO, Model model) {
//		NoticeVO findVO = noticeService.noticeInfo(noticeVO);
//		model.addAttribute("noticeInfo", findVO);
//		return "notice/noticeUpdate";
//	}
//	
//	// 수정 처리
//	@PostMapping("noticeUpdate")
//	@ResponseBody
//	public Map<String, Object> noticeUpdateProcess(@RequestBody NoticeVO noticeVO){
//		return noticeService.updateNotice(noticeVO);
//	}
//	
//	
//	// 삭제
//	@GetMapping("noticeDelete")
//	public String noticeDelete(@RequestParam Integer noticeNo) {
//		noticeService.deleteNotice(noticeNo);
//		return "redirect:noticeList";
//			
//	}
}
