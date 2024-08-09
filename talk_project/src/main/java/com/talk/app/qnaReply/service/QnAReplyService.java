package com.talk.app.qnaReply.service;

import java.util.List;
import java.util.Map;

public interface QnAReplyService {
	
	public List<QnAReplyVO> replyList();
	
	// 단건조회
	public QnAReplyVO replyInfo(QnAReplyVO replyVO);
	
	// 등록
	public int insertReply(QnAReplyVO replyVO);
	
	// 수정
	public Map<String, Object> updateReploy(QnAReplyVO replyVO);
	
	// 삭제
	public int deleteReply(int replyVO);
}
