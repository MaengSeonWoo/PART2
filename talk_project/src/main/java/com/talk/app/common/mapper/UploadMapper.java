package com.talk.app.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.talk.app.common.service.UploadFileVO;


public interface UploadMapper {
	// 파일 업로드
		void insertUploadedFile(UploadFileVO uploadFile);
		// 파일 조회
		List<UploadFileVO> selectFilesByDomain(@Param("domainType") String domainType, @Param("domainId") Long domainId);
		// 파일 Path 조회
		List<String> selectFilePathsByBoard(@Param("domainType") String domainType, @Param("domainId") Long domainId);
		// 파일 삭제
		void deleteFilesByDomain(@Param("domainType") String domainType, @Param("domainId") Long domainId);
}	
