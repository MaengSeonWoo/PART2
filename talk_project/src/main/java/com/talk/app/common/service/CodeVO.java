package com.talk.app.common.service;

import lombok.Data;

@Data
public class CodeVO {
	private String mainCode; // 실제 코드 값
	private String codeName; // 공통코드 이름
	private String codeRule; // 공통코드 분류
}
