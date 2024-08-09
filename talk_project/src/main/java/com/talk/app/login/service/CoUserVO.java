package com.talk.app.login.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CoUserVO {
	private Integer coUserNo;		//기업회원번호
	private Long coRegNo;			//사업자등록번호
	private String coName;			//기업명
	private String industry;		//업종
	private String repName;			//대표자
	@DateTimeFormat(pattern="yyyy-MM-dd")//파라미터 자동변환
	private Date estYear;			//설립연도
	private String coPostNo;		//우편번호
	private String coAddr;			//기본주소	
	private String coDetailAddr;	//상세주소
	private String mainBusiness;	//주요사업
	private String region;			//행정구역
	private String etc;				//기타사항
	private String coUserId;		//기업아이디
	private String coUserPw;		//비밀번호
	private String checkPw;			//비밀번호확인
	private String mgrName;			//담당자 이름
	private String mgrEmail;		//담당자 이메일
	private String mgrTel;			//담당자 연락처
	private String approvalStatus;	//승인상태
	private String coContact;		//기업연락처
	private String authority;		//권한
	private String logoImg;			//기업 로고
}
