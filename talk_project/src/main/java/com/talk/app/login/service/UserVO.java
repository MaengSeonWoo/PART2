package com.talk.app.login.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UserVO {			//일반회원
	private Integer userNo; 	//회원번호
	private String userName;	//이름
	private String userId;		//아이디
	private String userPw;		//비밀번호
	private String Tel;			//연락처
	private String postNo;		//우편번호
	private String addr;		//기본주소
	private String detailAddr;	//상세주소
	private String email;		//이메일
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birth;			//생년월일
	private String household;	//가구상황
	private String householdNm; //가구상황 공통코드 처리
	private String gender;		//성별
	private String authority;	//권한
	private Integer delStatus;		//회원탈퇴 상태
	private Timestamp statusUpdateTime; //회원탈퇴 처리시간 기록
	private String sido;		//회원 시도명
	private String sidoNm;		//회원 시도명 공통코드 처리
	private String likeSubject; //관심주제
	private String likeSubjectNm; //관심주제 공통코드 처리
	private String receive;		//문자,메일 수신여부
}
