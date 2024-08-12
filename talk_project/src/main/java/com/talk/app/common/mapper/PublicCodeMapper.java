package com.talk.app.common.mapper;

import java.util.List;

import com.talk.app.common.service.CodeVO;

public interface PublicCodeMapper {
	public List<CodeVO> selectCode(String codeRule);
}
