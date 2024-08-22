package com.talk.app.search.mapper;

import java.util.List;

import com.talk.app.search.service.mainSearchVO;

public interface SearchMapper {
	public List<mainSearchVO> searchAll(mainSearchVO searchVO);
}
