package com.talk.app.posting.service;

import java.util.List;
import java.util.Map;

import com.talk.app.common.service.Criteria;
import com.talk.app.mypage.service.ResumeVO;

public interface PostingService {
	public List<PostingVO> postingList(Criteria cri);
	
	public PostingVO postingInfo(int postingNo);
	
	public int getTotal();
	
	public Map<String, Object> applyResume(ResumeVO resumeVO);
}
