package com.talk.app.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.talk.app.mypage.service.CareerVO;
import com.talk.app.mypage.service.EduVO;
import com.talk.app.mypage.service.LicenseVO;
import com.talk.app.mypage.service.ResumeVO;

public interface ResumeMapper {
	// 마이페이지 이력서 리스트 조회
	public List<ResumeVO> selectResumeList(ResumeVO resumeVO);
	// 마이페이지 이력서 총 개수
	@Select("SELECT count(*) FROM resume WHERE user_no = (select user_no from users where user_id = #{userId})")
	public int getResumeTotal(String userId);
	// 마이페이지 이력서 상세 조회
	public ResumeVO selectResumeByNo(int resumeNo);
	// 마이페이지 이력서 등록
	public int insertResume(ResumeVO resume);
	// 이력서 등록 시 이미지이름 수정
	@Update("UPDATE resume SET resume_img = #{iname} WHERE resume_no = #{resumeNo}")
	public void updateImg(@Param("iname") String iname, @Param("resumeNo") int resumeNo);
	// 마이페이지 이력서 삭제
	@Delete("DELETE FROM resume WHERE resume_no = #{resumeNo}")
	public int deleteResume(int resumeNo);
	// 마이페이지 이력서 수정
	public int updateResume(ResumeVO resume);
	
	// 이력서 조회 수정시 사용자 검증
	public int getUserNoById(String userId);
	
	// 지원이력서 리스트
	public List<ResumeVO> selectApplyResumeList(String userId);
	
	// 경력사항 조회
	public List<CareerVO> selectCareers(int resumeNo);
	// 자격증 조회
	public List<LicenseVO> selectLicenses(int resumeNo);
	// 학력사항 조회
	public List<EduVO> selectEdus(int resumeNo);
	
	///// 수정
	// 경력사항 삭제
	public void deleteCareerByNo(int resumeNo);
	// 자격증 삭제
	public void deleteLicenseByNo(int resumeNo);
	// 학력사항 삭제
	public void deleteEduByNo(int resumeNo);
	
	// 경력사항 등록
	public void insertCareer(CareerVO careerVO);
	// 자격증 등록
	public void insertLicense(LicenseVO licenseVO);
	// 학력사항 등록
	public void insertEdu(EduVO eduVO);
	
	
}
