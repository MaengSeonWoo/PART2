package com.talk.app.video.service;

import java.util.List;
import java.util.Map;

public interface VideoService {
	// 전체조회
	public List<VideoVO> videoList();
	
	// 단건조회
	public VideoVO videoInfo(VideoVO videoVO);
	
	// 등록
	public int insertvideo(VideoVO videoVO);
	
	// 수정
	public Map<String, Object> updatevideo(VideoVO videoVO);
	
	// 삭제
	public int deletevideo(int videoVO);
	
	// 검색
	public List<VideoVO> searchVideo(VideoVO videoVO);
}
