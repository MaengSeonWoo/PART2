package com.talk.app.common.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talk.app.common.mapper.UploadMapper;
import com.talk.app.common.service.UploadFileVO;
import com.talk.app.common.service.UploadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

	@Value("${file.upload.path}")
	private String uploadPath;

	private final UploadMapper uploadedFileMapper;

	@Override
	public String imageUpload(MultipartFile[] uploadFiles, String domainType, Long domainId) {

		String saveName = "";
		String uploadFileName = "";
		// 파일 업로드 첫번째 값의 용량?이 0일때는 파일업로드 진행 안함
		if (uploadFiles.length > 0 && uploadFiles[0].getSize() > 0) {
			int i = 0;
			for (MultipartFile uploadFile : uploadFiles) {
				String originalName = uploadFile.getOriginalFilename();
				String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);
				// 날짜 폴더 생성
				String folderPath = makeFolder();
				// UUID
				String uuid = UUID.randomUUID().toString();
				// 저장할 파일 이름 중간에 "_"를 이용하여 구분
				uploadFileName = folderPath + File.separator + uuid + "_" + fileName;
				saveName = uploadPath + File.separator + uploadFileName;
				Path savePath = Paths.get(saveName);
				// Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
				System.out.println("path : " + saveName);
				try {
					uploadFile.transferTo(savePath);
					// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 경로에 \를 /로 변환작업
				String uploadFileName2 = setImagePath(uploadFileName);
				// 파일 정보를 데이터베이스에 저장
				UploadFileVO uploadedFile = new UploadFileVO();
				uploadedFile.setFileName(fileName);
				uploadedFile.setFileType(uploadFile.getContentType());
				uploadedFile.setFileSize(uploadFile.getSize());
				uploadedFile.setUploadTime(new Date());
				uploadedFile.setFilePath(uploadFileName2);
				uploadedFile.setDomainType(domainType);
				uploadedFile.setDomainId(domainId);
				uploadedFile.setFileOrder(i);

				uploadedFileMapper.insertUploadedFile(uploadedFile);
				i++;
			}
		}
		return uploadFileName;
	}

	// 이미지 업데이트
	@Override
	public String imageUpdate(MultipartFile[] uploadFiles, String domainType, Long domainId) {
		String saveName = "";
		if (uploadFiles != null && uploadFiles.length > 0 && uploadFiles[0].getSize() > 0) {
			// 기존 파일 삭제
			List<String> filePaths = uploadedFileMapper.selectFilePathsByBoard(domainType, domainId);
			for (String filePath : filePaths) {
				File file = new File(uploadPath + filePath);
				if (file.exists()) {
					if (file.delete()) {
						log.info("기존 파일 삭제 성공: {}", filePath);
					} else {
						log.error("기존 파일 삭제 실패: {}", filePath);
					}
				}
			}

			// 새 파일 업로드
			deleteFiles(domainType, domainId);
			saveName = imageUpload(uploadFiles, domainType, domainId);
		} else {
			log.info("업로드된 파일이 없습니다.");
		}
		return saveName;
	}

	@Override
	public List<UploadFileVO> selectFilesByDomain(String domainType, Long domainId) {
		return uploadedFileMapper.selectFilesByDomain(domainType, domainId);
	}

	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// LocalDate를 문자열로 포멧
		String folderPath = str.replace("/", File.separator);
		File uploadPathFoler = new File(uploadPath, folderPath);
		// File newFile= new File(dir,"파일명");
		if (uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs();
			// 만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미.
			// mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
			// mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
		}
		return folderPath;
	}

	private String setImagePath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}

	public void deleteFiles(String domainType, Long domainId) {
		List<UploadFileVO> uploadList = uploadedFileMapper.selectFilesByDomain(domainType, domainId);
		for (int i = 0; i < uploadList.size(); i++) {
			File file = new File(uploadPath + "/" + uploadList.get(i).getFilePath());
			if (file.exists()) {
				file.delete();
			}
		}
		uploadedFileMapper.deleteFilesByDomain(domainType, domainId);

	}

	@Override
	public String pdfData(String domainType, Long domainId, String filePath) {
		return uploadedFileMapper.selectPdf(domainType, domainId, filePath);
	}

}