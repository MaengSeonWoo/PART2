package com.talk.app.login.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CoUserVO {
	private Integer coUserNo;
	private Long coRegNo;
	private String coName;
	private String industry;
	private String repName;
	@DateTimeFormat(pattern="yyyy-MM-dd")//파라미터 자동변환
	private Date estYear;
	private String coPostNo;
	private String coAddr;
	private String coDetailAddr;
	private String mainBusiness;
	private String region;
	private String etc;
	private String coUserId;
	private String coUserPw;
	private String checkPw;
	private String mgrName;
	private String mgrEmail;
	private String mgrTel;
	private String approvalStatus;
	private String coContact;
	private String authority;
	private String logoImg;
}
