package com.talk.app.video.mapper;

import java.util.List;

import com.talk.app.video.service.VideoVO;

public interface VideoMapper {
	// 전체조회
	public List<VideoVO> selectVideoAll();
	
	// 단건조회
	public VideoVO selectVideoInfo(VideoVO videoVO);
	
	// 등록 
	public int insertVideo(VideoVO videoVO);
	
	// 수정 
	public int updateVideoInfo(VideoVO videoVO);
	
	// 삭제 : 조건 - no
	public int deleteVideoInfo(int videoNo);
}
