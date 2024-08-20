package com.talk.app.common.service;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Component
@Data
public class Criteria {

	private int pageNum; // 현재 페이지
	private int amount; // 페이지당 출력 데이터 개수
	private String type; // 검색 키워드
	private String keyword; // 검색 유형 ex) 전체, 제목, 작성자 등
	
	
	// 포스팅 검색 필드 추가
	private String sido; // 희망지역
	private String hopeAmount1; // 최저 급여
	private String hopeAmount2; // 최고 급여
	private String empType; // 고용 형태
	
	
	// 기본 페이지를 1페이지 10개씩 보여준다는 의미
	public Criteria() {
		this(1, 8);
		
	}

	// 매개변수로 들어오는 값을 이용하여 조정
	public Criteria(int pageNum, int amount) {
		super();
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	// UriComponentsBuilder를 이용하여 링크 생성
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
			.queryParam("pageNum", pageNum)
			.queryParam("amount", amount);
		
		return builder.toUriString();
		
	}
	
	public String[] getTypeArr() { // get으로 시작해야만 mybatis에서 찾을 수 있음
		return type == null ? new String[] {} : type.split("");
	}
	
	
	
	
}
