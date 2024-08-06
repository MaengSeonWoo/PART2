package com.talk.app.QnA.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;


public interface QnAService {
	// 전체조회
	public List<QnAVO> qnaList();
	
	// 단건조회
	public QnAVO qnaInfo(QnAVO qnaVO);
	
	// 등록
	public int insertQnA(QnAVO qnaVO);
	
	// 수정
	public Map<String, Object> updateQnA(QnAVO qnaVO);
	
	// 삭제 
	public int deleteQnA(QnAVO qnaVO);
}
