package com.talk.app.mypage.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EduVO {
	// 학력사항 번호
	private Integer eduNo;
	// 졸업년월
//	@DateTimeFormat(pattern = "yyyy-MM")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
	private Date graduation;
	// 학교명
	private String school;
	// 전공
	private String major;
	// 이력서 번호
	private int resumeNo;
	// 순번
	private int orders;
}
