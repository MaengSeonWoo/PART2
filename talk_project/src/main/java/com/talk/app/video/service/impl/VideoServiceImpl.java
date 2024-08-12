package com.talk.app.video.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.video.mapper.VideoMapper;
import com.talk.app.video.service.VideoService;
import com.talk.app.video.service.VideoVO;

@Service
public class VideoServiceImpl implements VideoService{
	
	private VideoMapper videoMapper;
	
	@Autowired
	VideoServiceImpl(VideoMapper videoMapper){
		this.videoMapper = videoMapper;
	}
	
	@Override
	public List<VideoVO> videoList() {
		return videoMapper.selectVideoAll();
	}

	@Override
	public VideoVO videoInfo(VideoVO videoVO) {
		return videoMapper.selectVideoInfo(videoVO);
	}

	@Override
	public int insertvideo(VideoVO videoVO) {
		int result = videoMapper.insertVideo(videoVO);
		
		return result == 1 ? videoVO.getVideoNo() : -1;
	}

	@Override
	public Map<String, Object> updatevideo(VideoVO videoVO) {
		Map<String, Object> map = new HashMap<>();
		boolean is = false;
		
		int result = videoMapper.updateVideoInfo(videoVO);
		if(result == 1) {
			is = true;
		}
		
		map.put("result", is);
		map.put("target", videoVO);
		return map;
	}

	@Override
	public int deletevideo(int videoVO) {
		return videoMapper.deleteVideoInfo(videoVO);
	}

}
