package com.talk.app.mypage.service;

import java.util.Date;

import lombok.Data;

@Data
public class LicenseVO {
	// 자격증 번호
	private Integer licenseNo;
	// 취득년월
	private Date getDate;
	// 기술/자격
	private String licenceName;
	//시행처
	private String licenceCompany;
	// 이력서 번호
	private int resumeNo;
	// 순번
	private int orders;
}
