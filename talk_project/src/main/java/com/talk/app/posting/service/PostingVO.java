package com.talk.app.posting.service;

import java.util.Date;

import lombok.Data;
/**
 * 채용 공고
 */
@Data
public class PostingVO {
	// 채용공고번호
	private Integer postingNo;
	// 업체명
	private String coName;
	// 근무지역
	private String workRegion;
	// 모집기간 시작일
	private Date startDate;
	// 모집기간 마감일
	private Date endDate;
	// 업종
	private String job;
	// 연령
	private int age;
	// 성별
	private String gender;
	// 고용형태
	private String empType;
	// 근무시간 일
	private String workDay;
	// 근무시간
	private String workTime;
	// 급여
	private int sal;
	// 모집인원
	private int party;
	// 근무내용
	private String workContent;
	// 기타내용
	private String etcContent;
	// 우대사항
	private String priContent;
	// 기업 회원 번호
	private int coUserNo;
	// 채용 공고 상태
	private String postingStatus;
	// 채용 공고 제목
	private String postingTitle;
}
