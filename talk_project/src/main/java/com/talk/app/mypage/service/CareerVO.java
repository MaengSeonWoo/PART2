package com.talk.app.mypage.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CareerVO {
	// 경력사항번호
	private Integer careerNo;
	// 근무처
	private String workPlace;
	// 직위/업무내용
	private String role;
	// 이력서번호
	private Integer resumeNo;
	// 순번
	private int orders;
	// 시작일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date careerStart;
	// 종료일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date careerEnd;
}
