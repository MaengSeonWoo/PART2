package com.talk.app.search.service;

import lombok.Data;

@Data
public class mainSearchVO {
	private String searchType;
	private String searchResult; // 검색제목
	private int searchNo; // 검색번호
	
	private String keyword;
}
