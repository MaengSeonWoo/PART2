package com.talk.app.posting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.talk.app.common.service.Criteria;
import com.talk.app.mypage.service.ResumeVO;
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
	public int getTotal(Criteria cri) {
		return postingMapper.getTotal(cri);
	}

	@Override
	public Map<String, Object> applyResume(ResumeVO resumeVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		int applyResuem = postingMapper.applyResuem(resumeVO);
		if(applyResuem == 1) {
			map.put("result", "success");
			map.put("resume", resumeVO);
		} else {
			map.put("result", "fail");
			map.put("resume", null);
		}
		
		return map;
	}

}
