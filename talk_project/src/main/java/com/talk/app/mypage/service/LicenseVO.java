package com.talk.app.mypage.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class LicenseVO {
	// 자격증 번호
	private Integer licenseNo;
	// 취득년월
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date getDate;
	// 기술/자격
	private String licenseName;
	//시행처
	private String licenseCompany;
	// 이력서 번호
	private int resumeNo;
	// 순번
	private int orders;
}
