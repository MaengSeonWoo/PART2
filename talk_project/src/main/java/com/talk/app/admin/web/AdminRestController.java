package com.talk.app.admin.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talk.app.admin.mapper.WelfareMapper;
import com.talk.app.admin.service.SampleService;
import com.talk.app.admin.vo.WelfareVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/samplerest")
//@RequiredArgsConstructor
public class AdminRestController {
	//test
    @Autowired
    private SampleService sampleService;
    
	@GetMapping(value = "/apitest", produces = MediaType.APPLICATION_XML_VALUE)
	public String callapihttp() throws Exception {
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B554287/LocalGovernmentWelfareInformations/LcgvWelfarelist"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + "odp3R%2BAnv93%2BqG0hMhsxznQIF589DFV7I%2BJbKgPbJu2h86CikqZnQN0weoWc9r1FZqDwWOL3YsDVzXFd%2BvX7%2Bw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("lifeArray","UTF-8") + "=" + URLEncoder.encode("006", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("arrgOrd","UTF-8") + "=" + URLEncoder.encode("001", "UTF-8"));
        
        URI uri = new URI(urlBuilder.toString());
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();

	}
	
	
	// 초기 API 호출 및 servId 저장하는 엔드포인트
    @GetMapping("/fetchServIds")
    public ResponseEntity<String> fetchAndSaveServIds() {
        try {
            sampleService.fetchAndSaveServId();
            return ResponseEntity.ok("ServIds fetched and saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }

    }

    // 상세 정보 가져와서 DB 업데이트하는 엔드포인트
    @GetMapping("/fetchDetails")
    public ResponseEntity<String> fetchAndUpdateDetails() {
        try {
            sampleService.fetchAndUpdateDetailInfo();
            return ResponseEntity.ok("Details fetched and updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
	
	

}
