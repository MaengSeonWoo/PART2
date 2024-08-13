package com.talk.app.QnA.web;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talk.app.QnA.service.QnAService;
import com.talk.app.QnA.service.qnaVO;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.qnaReply.service.QnAReplyService;
import com.talk.app.qnaReply.service.QnAReplyVO;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class QnAController {
	
	private final QnAService qnaService;
	private final QnAReplyService replyService;
	
	// 전체
	@GetMapping("qna")
	public String QnAList(Model model, Principal principal) {
		String coUserId = principal.getName();
		CoUserVO couserVO = new CoUserVO();
        couserVO.setCoUserId(coUserId);
		List<qnaVO> list = qnaService.qnaList();
		model.addAttribute("qnaList", list);
		return "qna/qnaList";
	}

	// 단건
	@GetMapping("qnaInfo")
	public String QnAInfo(qnaVO qnavo, Model model) {
		qnaVO findVO = qnaService.qnaInfo(qnavo);
		model.addAttribute("qnaInfo", findVO);
		System.out.println("답변달기전");
		
		List<QnAReplyVO> replyList = replyService.replyList();
		System.out.println("답변" + replyList);
		
		return "qna/qnaInfo";
	}
	
	// 삭제
	@GetMapping("qnaDelete")
	public String deleteQnA(@RequestParam Integer qnaNo) {
		qnaService.deleteQnA(qnaNo);
		return "redirect:qnaList";
	}
	
}
