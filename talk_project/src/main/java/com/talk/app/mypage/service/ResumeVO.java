package com.talk.app.mypage.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ResumeVO {
	// 이력서 번호
	private Integer resumeNo;
	// 이력서 제목
	private String resumeTitle;
	// 이름
	private String resumeName;
	// 생년월일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
	// 주소
	private String addr;
	// 전화번호
	private String tel;
	// 이메일
	private String email;
	// 자기소개서
	private String produce;
	// 작성일자
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	// 회원번호
	private int userNo;
	// 사진
	private String resumeImg;
	
	// 1대다 VO 리스트
	private List<CareerVO> careers;
	private List<EduVO> edus;
	private List<LicenseVO> licenses;
	
	// 지원 이력서
	private Integer applyNo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date applyDate;
	private String applyState;
	private int postingNo;
	private String result;
	private String userName;
	private String coName;
	
	
	
	
}
