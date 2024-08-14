package com.talk.app.mypage.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talk.app.mypage.mapper.ResumeMapper;
import com.talk.app.mypage.service.CareerVO;
import com.talk.app.mypage.service.EduVO;
import com.talk.app.mypage.service.LicenseVO;
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
	public ResumeVO resumeInfo(Integer resumeNo, String userId) {
		if(resumeMapper.getUserNoById(userId) == resumeMapper.selectResumeByNo(resumeNo).getUserNo()) {
			System.out.println(resumeMapper.selectResumeByNo(resumeNo).getResumeNo());
			return resumeMapper.selectResumeByNo(resumeNo);
		} else {
			return null;
		}
	}


	@Override
	public void saveResume(ResumeVO resume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void editResume(ResumeVO resumeVO) {
		// 이력서 기본 정보 업데이트
        resumeMapper.updateResume(resumeVO);
        
        // 기존 학력, 경력, 자격증 삭제
        resumeMapper.deleteEduByNo(resumeVO.getResumeNo());
        resumeMapper.deleteCareerByNo(resumeVO.getResumeNo());
        resumeMapper.deleteLicenseByNo(resumeVO.getResumeNo());

        // 새로운 학력, 경력, 자격증 삽입
        if (resumeVO.getEdus() != null) {
            for (EduVO edu : resumeVO.getEdus()) {
                edu.setResumeNo(resumeVO.getResumeNo());
                resumeMapper.insertEdu(edu);
            }
        }

        if (resumeVO.getCareers() != null) {
            for (CareerVO career : resumeVO.getCareers()) {
                career.setResumeNo(resumeVO.getResumeNo());
                resumeMapper.insertCareer(career);
            }
        }

        if (resumeVO.getLicenses() != null) {
            for (LicenseVO license : resumeVO.getLicenses()) {
                license.setResumeNo(resumeVO.getResumeNo());
                resumeMapper.insertLicense(license);
            }
        }
		
	}

	@Override
	public void removeResume(ResumeVO resume) {
		// TODO Auto-generated method stub
		
	}


}
