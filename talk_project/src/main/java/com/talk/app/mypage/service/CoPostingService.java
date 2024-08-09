package com.talk.app.mypage.service;

import java.util.List;
import java.util.Map;

import com.talk.app.posting.service.PostingVO;

public interface CoPostingService {
	// 전체조회
	public List<PostingVO> postingList();
	
	// 단건조회
	public PostingVO postingInfo(PostingVO postingVO);
	
	// 등록
	public int insertPosting(PostingVO postingVO);
	
	// 수정
	public Map<String, Object> updatePosting(PostingVO postingVO);
	
	// 삭제
	public int deletePosting(int postingNo);
}
