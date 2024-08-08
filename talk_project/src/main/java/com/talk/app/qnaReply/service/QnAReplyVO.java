package com.talk.app.qnaReply.service;

import java.util.Date;

import lombok.Data;

@Data
public class QnAReplyVO {
	private Integer replyNo; // 답변번호
	private String replyContent; // 답변내용
	private Date regDate; // 작성일
	private int qnaNo; // qna번호
}
