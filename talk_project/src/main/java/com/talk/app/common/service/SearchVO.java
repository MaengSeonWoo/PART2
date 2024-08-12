package com.talk.app.common.service;

import java.util.List;

import lombok.Data;

@Data
public class SearchVO {
	private int pageNum=1; // 현재 페이지
	private int amount=10; // 페이지당 출력 데이터 개수
	private String keyword;
	private String searchCondition;
	private String sido;
	private List<String> likeSubject;
	private String start;
	private String end;
	private String title;
    private String wid; // 필요에 따라 추가

}
