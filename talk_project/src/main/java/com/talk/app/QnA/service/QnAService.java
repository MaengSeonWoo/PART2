package com.talk.app.QnA.service;

import java.util.List;
import java.util.Map;

public interface QnAService {
	// 전체조회
	public List<qnaVO> qnaList(String role);
	
	// 본인 전체조회
	/* public List<qnaVO> myQnaList(qnaVO qnavo); */
	
	// 단건조회
	public qnaVO qnaInfo(qnaVO qnavo);
	
	// 등록
	public int insertQnA(qnaVO qnavo);
	
	// 수정
	public Map<String, Object> updateQnA(qnaVO qnavo);
	
	// 삭제
	public int deleteQnA(int qnavo);
}
