package com.talk.app.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talk.app.admin.service.SampleService;

@RestController
@RequestMapping("/samplerest")
public class AdminRestController {

	@Autowired
	private SampleService sampleService;

    @Scheduled(cron = "0 0 0 * * *")
    public void processScheduledDeletion() {
        try {
			sampleService.fetchAndSaveWelfareData();
		} catch (Exception e) {
			e.printStackTrace();
		}
        try {
			sampleService.fetchAndUpdateWelfareDetails();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @PostMapping("/fetchServIds")
    public ResponseEntity<String> fetchAndSaveServIds() {
        try {
            sampleService.fetchAndSaveWelfareData();
            return ResponseEntity.ok("서비스ok");
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/detail")
    public ResponseEntity<String> detailData() {
        try {
            sampleService.fetchAndUpdateWelfareDetails();
            return ResponseEntity.ok("서비스ok");
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
