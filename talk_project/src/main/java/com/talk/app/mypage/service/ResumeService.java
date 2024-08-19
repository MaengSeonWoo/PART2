package com.talk.app.mypage.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
	// 이력서 리스트
	public List<ResumeVO> resumeList(ResumeVO resumeVO);
	// 이력서 총 개수
	public int getResumeTotal(String userId);
	// 지원이력서 리스트
	public List<ResumeVO> applyResumeList(ResumeVO resumeVO);
	// 지원 이력서 총 개수
	public int getApplyResumeTotal(String userId);
	// 이력서 상세조회
	public ResumeVO resumeInfo(Integer resumeNo, String userId);
	// 이력서 등록
	public void saveResume(ResumeVO resume, MultipartFile[] uploadFiles, String userId);
	// 이력서 수정
	public void editResume(ResumeVO resumeVO);
	// 이력서 삭제
	public void removeResume(Integer resumeNo);
}
