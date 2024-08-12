package com.talk.app.common.mapper;

import java.util.List;

import com.talk.app.common.service.CodeVO;

public interface PublicCodeMapper {
	public List<CodeVO> selectCode(String codeRule); // codeRule에 따라 공통코드 리스트 조회
}
