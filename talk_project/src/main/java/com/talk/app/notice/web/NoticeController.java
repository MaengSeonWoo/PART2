package com.talk.app.notice.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talk.app.common.service.UploadFileVO;
import com.talk.app.common.service.UploadService;
import com.talk.app.notice.service.NoticeService;
import com.talk.app.notice.service.NoticeVO;

import lombok.RequiredArgsConstructor;
/*
 * 작성자 : 최석원
 * 작성일자: 2024-08-08
 * 공지사항 관리 : 조회, 등록, 수정, 삭제
 */



@Controller
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	
	private final UploadService uploadServcie;
	
	// 전체조회
	/*
	 * @GetMapping("noticeList") public String NoticeList(Model model) {
	 * List<NoticeVO> list = noticeService.noticeList();
	 * model.addAttribute("noticeList", list);
	 * 
	 * return "notice/noticeList"; }
	 */
	
	// 단건조회
	@GetMapping("noticeInfo")
	public String noticeInfo(NoticeVO noticeVO, Model model) {
		NoticeVO findVO = noticeService.noticeInfo(noticeVO);
		noticeService.plusViewCnt(noticeVO.getNoticeNo());
		model.addAttribute("noticeInfo", findVO);
		List<UploadFileVO> images = uploadServcie.selectFilesByDomain("Notice", (long) noticeVO.getNoticeNo() );
		model.addAttribute("file", images);
		return "notice/noticeInfo";
	}
	
	// 등록
	@GetMapping("noticeInsert")
	public String noticeInsertForm() {
		return "notice/noticeInsert";
	}
	
	// 등록 처리
	@PostMapping("noticeInsert")
	public String noticeInsertProcess(NoticeVO noticeVO, @RequestPart MultipartFile[] uploadFiles) {
		int nno = noticeService.insertNotice(noticeVO);
		
		uploadServcie.imageUpload(uploadFiles, "Notice", (long) nno);
		
		return "redirect:noticeInfo?noticeNo=" + nno;
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
	public Map<String, Object> noticeUpdateProcess(NoticeVO noticeVO, @RequestPart MultipartFile[] uploadFiles){
		uploadServcie.imageUpdate(uploadFiles, "Notice", (long) noticeVO.getNoticeNo());
		return noticeService.updateNotice(noticeVO);
	}
	
	
	// 삭제
	@GetMapping("noticeDelete")
	public String noticeDelete(@RequestParam Integer noticeNo) {
		uploadServcie.deleteFiles("Notice", (long) noticeNo);		
		noticeService.deleteNotice(noticeNo);
		return "redirect:noticeList";
	}

	// 검색
	@GetMapping("noticeList")
	public String search(NoticeVO noticeVO, Model model) {
		List<NoticeVO> searchList = noticeService.serachNotice(noticeVO);
		model.addAttribute("noticeList", searchList);
		
		return "notice/noticeList";
	}
	
	
}
