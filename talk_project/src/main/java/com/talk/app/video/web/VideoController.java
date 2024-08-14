package com.talk.app.video.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.video.service.VideoService;
import com.talk.app.video.service.VideoVO;

import lombok.RequiredArgsConstructor;




/*
	작성자 : 최석원
	작성일자 : 2024-08-12
	동영상 관리 : 조회, 등록, 수정, 삭제, 검색

*/
@Controller
@RequiredArgsConstructor
public class VideoController {

	private final VideoService videoService;
	
	// 전체조회
/*	@GetMapping("videoList")
	public String videoList(Model model) {
		List<VideoVO> vList = videoService.videoList();
		model.addAttribute("videoList", vList);
		return "video/videoList";
	}*/
	
	
	// 단건조회
	@GetMapping("videoInfo")
	public String videoInfo(VideoVO videoVO, Model model) {
		VideoVO findVO = videoService.videoInfo(videoVO);
		model.addAttribute("videoInfo", findVO);
		return "video/videoInfo";
	}
	
	// 등록
	@GetMapping("videoInsert")
	public String videoInsertForm() {
		return "video/videoInsert";
	}
	
	// 등록처리
	@PostMapping("videoInsert")
	public String videoInsertProcess(VideoVO videoVO) {
		int vi = videoService.insertvideo(videoVO);
		
		return "redirect:videoInfo?videoNo=" + vi;
	}
	
	// 수정
	@GetMapping("videoUpdate")
	public String videoUpdateForm(VideoVO videoVO, Model model) {
		VideoVO findVO = videoService.videoInfo(videoVO);
		model.addAttribute("videoInfo", findVO);
		return "video/videoUpdate";
	}
	
	// 수정 처리
	@PostMapping("videoUpdate")
	@ResponseBody
	public Map<String, Object> videoUpdate(@RequestBody VideoVO videoVO){
		return videoService.updatevideo(videoVO);
	}
	
	// 삭제 
	@GetMapping("videoDelete")
	public String videoDelete(@RequestParam Integer videoNo) {
		videoService.deletevideo(videoNo);
		return "redirect:videoList";
	}
	
	// 검색
	@GetMapping("videoList")
	public String searchVideo(VideoVO videoVO, Model model) {
		List<VideoVO> serachResult = videoService.searchVideo(videoVO);
		model.addAttribute("videoList", serachResult);
		
		return "video/videoList";
	}
}
