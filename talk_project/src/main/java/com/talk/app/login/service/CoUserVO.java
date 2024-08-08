package com.talk.app.login.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CoUserVO {
	private Integer CoUserNo;
	private Long CoRegNo;
	private String CoName;
	private String Industry;
	private String RepName;
	@DateTimeFormat(pattern="yyyy-MM-dd")//파라미터 자동변환
	private Date EstYear;
	private String CoPostNo;
	private String CoAddr;
	private String CoDetailAddr;
	private String MainBusiness;
	private String Region;
	private String Etc;
	private String CoUserId;
	private String CoUserPw;
	private String CheckPw;
	private String MgrName;
	private String MgrEmail;
	private String MgrTel;
	private String ApprovalStatus;
	private String CoContact;
	private String Authority;
}
