package com.talk.app.notice.mapper;

import java.util.List;

import com.talk.app.notice.service.NoticeVO;

public interface NoticeMapper {

	// 전체조회
	public List<NoticeVO> selectNoticeAll();
	
	// 단건조회
	public NoticeVO selectNoticeInfo(NoticeVO noticeVO);
	
	// 등록 
	public int insertNoticeInfo(NoticeVO noticeVO);
	
	// 수정 
	public int updateNoticeInfo(NoticeVO noticeVO);
	
	// 삭제 : 조건 - no
	public int deleteNoticeInfo(int noticeNo);
	
	// 조회수 증가
	public int plusViewCnt(int viewCnt);
	
	// 검색
	public List<NoticeVO> searchResult(NoticeVO noticeVO);
	
}
