package com.talk.app.notice.service;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeVO {
	private Integer noticeNo;
	private String noticeTitle;
	private String noticeContent;
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date createDate;
	private int viewCnt;
	private int imNotice;
}
