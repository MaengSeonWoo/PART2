package com.talk.app.qna.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.talk.app.qna.service.QnAService;
import com.talk.app.qna.service.qnaVO;
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
	public String QnAList(Model model) {
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
		
//		List<QnAReplyVO> replyList = replyService.replyList(qnavo.getQnaNo());
//		System.out.println("답변" + replyList);
//		model.addAttribute("reply", replyList);
		
		return "qna/qnaInfo";
	}
	
}
