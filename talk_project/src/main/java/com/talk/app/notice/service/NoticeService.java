package com.talk.app.notice.service;

import java.util.List;
import java.util.Map;



public interface NoticeService {
	// 전체조회
	public List<NoticeVO> noticeList();
	
	// 단건조회
	public NoticeVO noticeInfo(NoticeVO noticeVO);
	
	// 등록
	public int insertNotice(NoticeVO noticeVO);
	
	// 수정
	public Map<String, Object> updateNotice(NoticeVO noticeVO);
	
	// 삭제
	public int deleteNotice(int noticeVO);
	
	// 조회수
	public int plusViewCnt(int noticeVO);
	
	// 검색
	public List<NoticeVO> serachNotice(String noticeVO);
}
