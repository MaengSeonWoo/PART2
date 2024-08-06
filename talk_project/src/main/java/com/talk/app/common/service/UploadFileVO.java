package com.talk.app.common.service;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UploadFileVO {
	private Long fileNo;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadTime;
    private String filePath;
    private String domainType;
    private Long domainId;
    private int fileOrder;
}
