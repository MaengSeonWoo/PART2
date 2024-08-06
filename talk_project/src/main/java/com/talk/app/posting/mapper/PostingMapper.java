package com.talk.app.posting.mapper;

import java.util.List;

import com.talk.app.posting.service.PostingVO;

public interface PostingMapper {
	// 채용공고 리스트 조회
	public List<PostingVO> selectPostingList();
	
	// 채용 공고 상세 조회
	public PostingVO selectPostingInfo(int postingNo);
	
}
