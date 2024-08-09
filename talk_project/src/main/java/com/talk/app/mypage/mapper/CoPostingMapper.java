package com.talk.app.mypage.mapper;

import java.util.List;

import com.talk.app.posting.service.PostingVO;

public interface CoPostingMapper {
	// 채용공고 전체조회
	public List<PostingVO> selectPostingAll();
	
	// 단건조회
	public PostingVO selectPostingInfo(PostingVO postingVO);
	
	// 등록
	public int insertPostingInfo(PostingVO postingVO);
	
	// 수정
	public int updatePostingInfo(PostingVO postingVO);
	
	// 삭제
	public int deletePostingInfo(int postingNo);
}
