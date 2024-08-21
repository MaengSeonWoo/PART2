package com.talk.app.mypage.service;

import java.util.List;

public interface CoResumeService {
	// 제출된 이력서 조회
	public List<ResumeVO> coResumeList(String coUserId);
	
	// 제출된 이력서 상세조회
	public ResumeVO coResumeInfo(int resumeNo);
}
