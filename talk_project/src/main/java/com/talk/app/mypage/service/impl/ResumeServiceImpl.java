package com.talk.app.mypage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talk.app.mypage.mapper.ResumeMapper;
import com.talk.app.mypage.service.ResumeService;
import com.talk.app.mypage.service.ResumeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService{
	
	private final ResumeMapper resumeMapper;
	
	@Override
	public List<ResumeVO> resumeList(String userId) {
		return resumeMapper.selectResumeList(userId);
	}
	
	@Override
	public List<ResumeVO> applyResumeList(String userId) {
		return resumeMapper.selectApplyResumeList(userId);
	}

	@Override
	public ResumeVO resumeInfo(int resumeNo) {
		return resumeMapper.selectResumeByNo(resumeNo);
	}

	@Override
	public void saveResume(ResumeVO resume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editResume(int resumeNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeResume(ResumeVO resume) {
		// TODO Auto-generated method stub
		
	}


}
