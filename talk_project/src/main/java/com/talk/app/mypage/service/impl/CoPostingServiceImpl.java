package com.talk.app.mypage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.talk.app.mypage.mapper.CoPostingMapper;
import com.talk.app.mypage.service.CoPostingService;
import com.talk.app.posting.service.PostingVO;

public class CoPostingServiceImpl implements CoPostingService{
	
	private CoPostingMapper copostingMapper;
	
	@Autowired
	public CoPostingServiceImpl(CoPostingMapper copostingMapper) {
		this.copostingMapper = copostingMapper;
	}

	@Override
	public List<PostingVO> postingList() {
		// TODO Auto-generated method stub
		return copostingMapper.selectPostingAll();
	}

	@Override
	public PostingVO postingInfo(PostingVO postingVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertPosting(PostingVO postingVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Object> updatePosting(PostingVO postingVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deletePosting(int postingNo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
