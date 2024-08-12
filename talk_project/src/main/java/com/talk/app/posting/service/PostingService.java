package com.talk.app.posting.service;

import java.util.List;

import com.talk.app.common.service.Criteria;

public interface PostingService {
	public List<PostingVO> postingList(Criteria cri);
	
	public PostingVO postingInfo(int postingNo);
	
	public int getTotal();
}
