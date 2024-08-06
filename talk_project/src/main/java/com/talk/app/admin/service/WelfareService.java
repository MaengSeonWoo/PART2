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
	//입력
	public int welfareInsert2(WelfareVO vo);
	
	public Map<String, Object> welfareUpdate(WelfareVO vo);
	
	public int welfareDelete(int wid);
	
	
	
}
