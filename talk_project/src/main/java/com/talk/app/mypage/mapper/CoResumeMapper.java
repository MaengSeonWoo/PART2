package com.talk.app.mypage.mapper;

import java.util.List;

import com.talk.app.mypage.service.CareerVO;
import com.talk.app.mypage.service.EduVO;
import com.talk.app.mypage.service.LicenseVO;
import com.talk.app.mypage.service.ResumeVO;

public interface CoResumeMapper {
	// 제출된 이력서 리스트 조회
	public List<ResumeVO> selectCoResumeList(String coUserId);
	
	// 제출된 이력서 상세 조회
	public ResumeVO selectCoResumeByNo(int resumeNo);
	
	// 경력사항 조회
	public List<CareerVO> applyCareers(int resumeNo);
	
	// 자격증 조회
	public List<LicenseVO> applyLicenses(int resumeNo);
	
	// 학력사항 조회
	public List<EduVO> applyEdus(int resumeNo);
}
