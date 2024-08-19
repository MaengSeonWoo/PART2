package com.talk.app.mypage.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.talk.app.common.service.UploadService;
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
	private final UploadService uploadService;
	
	@Override
	public List<ResumeVO> resumeList(ResumeVO resumeVO) {
		return resumeMapper.selectResumeList(resumeVO);
	}
	
	@Override
	public List<ResumeVO> applyResumeList(ResumeVO resumeVO) {
		return resumeMapper.selectApplyResumeList(resumeVO);
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
	@Transactional
	public void saveResume(ResumeVO resumeVO, MultipartFile[] uploadFiles, String userId) {
		String saveName = " ";
		int userNo = resumeMapper.getUserNoById(userId);
        resumeVO.setResumeImg(saveName);
        resumeVO.setUserNo(userNo);
        System.out.println(resumeVO);
        // 이력서 저장
        resumeMapper.insertResume(resumeVO);

        // 경력 저장
        List<CareerVO> careers = resumeVO.getCareers();
        if (careers != null) {
            for (CareerVO career : careers) {
                career.setResumeNo(resumeVO.getResumeNo());  // 이력서 번호 설정
                resumeMapper.insertCareer(career);
            }
        }

        // 자격증 저장
        List<LicenseVO> licenses = resumeVO.getLicenses();
        if (licenses != null) {
            for (LicenseVO license : licenses) {
                license.setResumeNo(resumeVO.getResumeNo());  // 이력서 번호 설정
                resumeMapper.insertLicense(license);
            }
        }

        // 학력 저장
        List<EduVO> edus = resumeVO.getEdus();
        if (edus != null) {
            for (EduVO edu : edus) {
                edu.setResumeNo(resumeVO.getResumeNo());  // 이력서 번호 설정
                resumeMapper.insertEdu(edu);
            }
        }
        // 파일 로직 구현
        saveName = uploadService.imageUpload(uploadFiles, "RESUME", (long)resumeVO.getResumeNo());
        resumeMapper.updateImg(saveName, resumeVO.getResumeNo());
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
	public void removeResume(Integer resumeNo) {
		resumeMapper.deleteResume(resumeNo);
	}

	@Override
	public int getResumeTotal(String userId) {
		return resumeMapper.getResumeTotal(userId);
	}

	@Override
	public int getApplyResumeTotal(String userId) {
		// TODO Auto-generated method stub
		return resumeMapper.getApplyResumeTotal(userId);
	}


}
