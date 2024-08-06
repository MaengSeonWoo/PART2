package com.talk.app.notice.service;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeVO {
	private Integer noticeNo; // 번호
	private String noticeTitle; // 제목
	private String noticeContent; // 내용
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date createDate; // 작성일
	private int viewCnt; // 조회수
	private int imNotice; // 중요, 일반 공지
}
