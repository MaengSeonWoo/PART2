package com.talk.app.QnA.mapper;

import java.util.List;

import com.talk.app.QnA.service.QnAVO;

public interface QnAMapper {
	// 전체조회
	public List<QnAVO> selectQnaAll();
	
	// 단건조회
	public QnAVO selectQnAInfo(QnAVO qnaVO);
	
	// 등록
	public int insertQnAInfo(QnAVO qnaVO);
	
	// 수정
	public int updateQnAInfo(QnAVO qnaVO);
	
	// 삭제 
	public int deleteQnAInfo(QnAVO qnaNo);
}
