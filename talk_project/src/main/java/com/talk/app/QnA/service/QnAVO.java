package com.talk.app.QnA.service;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class QnAVO {
	private Integer qnaNo; // 번호
	private String qnaTitle; // 제목
	private String qnaContent; // 내용
	@DateTimeFormat(pattern="yyyy/MM//dd")
	private Date regDate; // 작성일
	private String qnaState; // 상태
	private int userNo; // 유저번호
	private int coUserNo; // 기업유저번호
}
