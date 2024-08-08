package com.talk.app.posting.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talk.app.common.service.Criteria;
import com.talk.app.posting.mapper.PostingMapper;
import com.talk.app.posting.service.PostingService;
import com.talk.app.posting.service.PostingVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingServiceImpl implements PostingService{

	private final PostingMapper postingMapper;
	
	@Override
	public List<PostingVO> postingList(Criteria cri) {
		return postingMapper.selectPostingList(cri);
	}

	@Override
	public PostingVO postingInfo(int postingNo) {
		return postingMapper.selectPostingInfo(postingNo);
	}

	@Override
	public int getTotal() {
		return postingMapper.getTotal();
	}

}
