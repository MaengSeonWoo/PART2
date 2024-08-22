package com.talk.app.admember.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.talk.app.admember.service.MemberService;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.posting.service.PostingVO;

@Controller
@RequestMapping("admin/approve")
public class MemberController {

	@Autowired
	MemberService service;
	
	//기업회원 승인목록(기업목록 전체 신청desc처리)
	@GetMapping("")
	public String approveAll(Model model, CoUserVO vol) {
		List<CoUserVO> list = service.approveAll();
		model.addAttribute("list",list);
		return "admember/approvelist";
	}
	
	//기업회원 가입신청 상세
	@GetMapping("detail")
	public String approveDetail(Model model, CoUserVO vo) {
		CoUserVO findVO = service.coDetail(vo);
		model.addAttribute("detail", findVO);
		return "admember/coDetail";
	}
	
	//기업회원 승인 => 
	@GetMapping("confirm")
	public String updateCo(int coUserNo) {
		int wid = service.coUpdate(coUserNo);
		return "redirect:detail?coUserNo="+ wid;
		
	}
	
	//기업회원 거절(승인상태변경,데이터 지우기)
	
	
	//채용등록 리스트
	@GetMapping("postlist")
	public String postlist(Model model, PostingVO vo) {
		List<PostingVO> list = service.postAll();
		model.addAttribute("post",list);
		return "admember/postlist";
	}
	
	//채용등록 상세
	@GetMapping("postdetail")
	public String detailPost(Model model, PostingVO vo) {
		PostingVO findVO = service.postdetail(vo);
		model.addAttribute("detail",findVO);
		return "admember/postdetail";
	}
	
	//채용등록 승인
	@GetMapping("postingok")
	public String updatePost(int postingNo) {
		int pid = service.postUpdate(postingNo);
		return "redirect:postdetail?postingNo="+pid;
	}
	
	
	
	//일반회원전체
	@GetMapping("user")
	public String userAll(Model model, UserVO vo) {
		List<UserVO> list = service.userAll();
		model.addAttribute("ulist",list);
		return "admember/user";
	}
	
	
}
