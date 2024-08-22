package com.talk.app.admin.service;

import lombok.Data;

@Data
public class WelfareVO {
	private int wid;
	private String servId;		//서비스아이디
	private String servName;	//서비스명
	private String supPeriod;	//지원주기명
	private String provType;	//제공유형명
	private String bizDept;		//사업담당부서명
	private String sido;			//시도명
	private String servSummary;//서비스 요약
	private String likeSubject;	//관심주제명
	private String sgg;			//시군구명
	private String household;	//가구상황명

	private String startDate;	//시행시작일자
	private String endDate;		//시행종료일자
	private String appWay;		//신청방법 내용
	private String supTarget;	//지원대상 내용
	private String salServ;		//급여서비스 내용
	private String selStandard;//선정기준 내용
	
	
	// 
	private Integer userNo;

}
