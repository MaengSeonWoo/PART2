package com.talk.app.QnA.web;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String QnAList(Model model, Principal principal, qnaVO qnavo) {
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
		
		return "qna/qnaInfo";
	}
	// 등록
	@GetMapping("qnaInsert")
	public String qnaInsertForm() {
		return "qna/qnaInsert";
	}
	
	// 등록 처리
	@PostMapping("qnaInsert")
	public String qnaInsert(qnaVO qnavo) {
		
		int nno = qnaService.insertQnA(qnavo);
		
		return "redirect:qnaInfo?qnaNo=" + nno;
	}
	
	// 댓글등록
	/*
	 * @PostMapping("qnaInfo") public String replyInsert(QnAReplyVO replyVO, Model
	 * model) { List<QnAReplyVO> findVO = replyService.replyList();
	 * model.addAttribute("reply", findVO);
	 * 
	 * return "qna/qnaInfo"; }
	 */
	
	// 삭제
	@GetMapping("qnaDelete")
	public String deleteQnA(@RequestParam Integer qnaNo) {
		qnaService.deleteQnA(qnaNo);
		return "redirect:qnaList";
	}
	
}
