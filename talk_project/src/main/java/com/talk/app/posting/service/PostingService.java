package com.talk.app.posting.service;

import java.util.List;

public interface PostingService {
	public List<PostingVO> postingList();
	
	public PostingVO postingInfo(int postingNo);
}
