package com.talk.app.mypage.service;

import java.util.List;

public interface ResumeService {
	// 이력서 리스트
	public List<ResumeVO> resumeList(String userId);
	// 지원이력서 리스트
	public List<ResumeVO> applyResumeList(String userId);
	// 이력서 상세조회
	public ResumeVO resumeInfo(int resumeNo);
	// 이력서 등록
	public void saveResume(ResumeVO resume);
	// 이력서 삭제
	public void editResume(int resumeNo);
	// 이력서 수정
	public void removeResume(ResumeVO resume);
}
