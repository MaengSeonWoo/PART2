package com.talk.app.ocr.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talk.app.ocr.service.NaverOcrApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OcrController {
    @Value("${naver.service.secretKey}")
    private String secretKey;
    private final NaverOcrApi naverApi;

    // 파일 업로드 폼을 보여주기 위한 GET 요청 핸들러 메서드
    @GetMapping("upload-form")
    public String uploadForm() throws Exception {
        return "mypage/upload-form"; // HTML 템플릿의 이름을 반환 (upload-form.html)
    }

    // 파일 업로드 및 OCR 수행을 위한 POST 요청 핸들러 메서드
    @PostMapping("uploadAndOcr")
    @ResponseBody
    public List<String> uploadAndOcr(@RequestParam("file") MultipartFile file) throws IOException {

        String naverSecretKey = secretKey;

        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);

        List<String> result = naverApi.callApi("POST", tempFile.getPath(), naverSecretKey, "jpg");

        tempFile.delete();

        return result;
    }
}
