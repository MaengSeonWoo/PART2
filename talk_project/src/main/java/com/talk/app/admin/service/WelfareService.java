package com.talk.app.admin.service;

import java.util.List;
import java.util.Map;

public interface WelfareService {
	//크롤링
	public int welfareInsert(WelfareVO vo);
	//전체
	public List<WelfareVO> welfareList();
	//상세
	public WelfareVO welfareDetail(WelfareVO vo);
	//상세2
	public int welfareDetail2(int wid);
	//입력
	public int welfareInsert2(WelfareVO vo);
	//수정
	public Map<String, Object> welfareUpdate(WelfareVO vo);
	//삭제
	public int welfareDelete(int wid);
	//문자전송
	public List<UserWelfareVO> userMsg(UserWelfareVO vo);
	//전송한 문자결과
	public List<UserWelfareVO> msgResult(int wid);

	//기업 미승인 목록만
	//일반 qna 댓글안달림
	
	//기업 qna 댓글안달림
	
	
}
