package com.talk.app.mypage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.mypage.mapper.CoPostingMapper;
import com.talk.app.mypage.service.CoPostingService;
import com.talk.app.posting.service.PostingVO;

@Service
public class CoPostingServiceImpl implements CoPostingService{
	
	private CoPostingMapper copostingMapper;
	
	@Autowired
	public CoPostingServiceImpl(CoPostingMapper copostingMapper) {
		this.copostingMapper = copostingMapper;
	}

	@Override
	public List<PostingVO> postingList(String coUserId) {
		// TODO Auto-generated method stub
		return copostingMapper.selectPostingAll(coUserId);
	}

	@Override
	public PostingVO postingInfo(PostingVO postingVO) {
		// TODO Auto-generated method stub
		return copostingMapper.selectPostingInfo(postingVO);
	}

	@Override
	public int insertPosting(PostingVO postingVO) {
		int result = copostingMapper.insertPostingInfo(postingVO);
		
		return result == 1 ? postingVO.getPostingNo() : -1;
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
	
	@Override
	public int getCoUserNoById(String coUserId) {
	    return copostingMapper.getCoUserNoById(coUserId);
	}

}
