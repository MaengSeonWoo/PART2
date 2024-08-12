package com.talk.app.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.common.mapper.PublicCodeMapper;

@Service
public class PublicCodeService {
	
	@Autowired
	private PublicCodeMapper publiccodeMapper;
	
	
	public List<CodeVO> selectCode(String codeRule){
		return publiccodeMapper.selectCode(codeRule);
	}
}
