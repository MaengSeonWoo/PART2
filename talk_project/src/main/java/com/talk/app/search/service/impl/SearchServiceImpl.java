package com.talk.app.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.search.mapper.SearchMapper;
import com.talk.app.search.service.SearchService;
import com.talk.app.search.service.mainSearchVO;

@Service
public class SearchServiceImpl implements SearchService {
	
	private SearchMapper searchMapper;
	
	@Autowired
	SearchServiceImpl(SearchMapper searchMapper){
		this.searchMapper = searchMapper;
	}
	
	@Override
	public List<mainSearchVO> searchAll(mainSearchVO searchVO) {
		return searchMapper.searchAll(searchVO);
	}

}
