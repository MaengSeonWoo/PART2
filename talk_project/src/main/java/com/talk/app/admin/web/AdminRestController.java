package com.talk.app.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talk.app.admin.service.SampleService;

@RestController
@RequestMapping("/samplerest")
//@RequiredArgsConstructor
public class AdminRestController {


    @Autowired
    private SampleService sampleService;

    @PostMapping("/fetchServIds")
    public ResponseEntity<String> fetchAndSaveServIds() {
        try {
            sampleService.fetchAndSaveWelfareData();
            return ResponseEntity.ok("ServIds fetched and saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
