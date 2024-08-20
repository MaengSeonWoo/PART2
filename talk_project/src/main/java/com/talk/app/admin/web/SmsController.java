//package com.talk.app.admin.web;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.talk.app.admin.service.SmsService;
//
//@RestController
//public class SmsController {
//	
//
//    @Autowired
//    private SmsService smsService;
//
//    @GetMapping("/send-sms")
//    public String sendSms(@RequestParam String to, @RequestParam String text) {
//        smsService.sendSms(to, text);
//        return "SMS Sent!";
//    }
//}
