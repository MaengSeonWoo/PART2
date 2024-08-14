package com.talk.app.QnA.service;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class qnaVO {
	private Integer qnaNo; // 글 번호 
	private String qnaTitle; // 제목
	private String qnaContent; // 내용
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date regDate; // 작성일
	private String qnaState; // 답변상태
	private int userNo; // 회원번호
	private int coUserNo; // 기업회원번호
	private String replyContent; // 댓글내용
}
