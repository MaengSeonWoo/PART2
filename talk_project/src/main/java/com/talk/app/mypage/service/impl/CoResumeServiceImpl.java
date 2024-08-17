package com.talk.app.mypage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talk.app.mypage.mapper.CoResumeMapper;
import com.talk.app.mypage.service.CoResumeService;
import com.talk.app.mypage.service.ResumeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoResumeServiceImpl implements CoResumeService{
	
	private final CoResumeMapper coresumeMapper;

	@Override
	public List<ResumeVO> coResumeList(String coUserId) {
		return coresumeMapper.selectCoResumeList(coUserId);
	}

	@Override
	public ResumeVO coResumeInfo(int resumeNo) {
		return coresumeMapper.selectCoResumeByNo(resumeNo);
	}
	
}
