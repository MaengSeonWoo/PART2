package com.talk.app.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.talk.app.admin.mapper.WelfareMapper;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;


@Service
public class EmailService {
	@Value("${mailgun.api.key}")
    private String apiKey;

	  @Value("${mailgun.domain.name}")
	    private String domainName;
	  
	  
    @Autowired
    WelfareMapper mapper;  // CoUserMapper를 주입받아 사용합니다

    public void sendSimpleMessage(int coUserNo) {
        // coUserNo로 이메일 주소 가져오기
        String to = mapper.sendMail(coUserNo);
        
        if (to != null && !to.isEmpty()) {
            try {
                HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + domainName + "/messages")
                        .basicAuth("api", apiKey)
                        .field("from", "Excited User <mailgun@" + domainName + ">")
                        .field("to", to)  // 동적으로 받은 이메일 주소 사용
                        .field("subject", "Hello")
                        .field("text", "Testing Mailgun API")
                        .asJson();
                
                System.out.println("Response Status: " + request.getStatus());
                System.out.println("Response Body: " + request.getBody().toString());

                System.out.println("Email sent to: " + to);
                System.out.println(request.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No email address found for coUserNo: " + coUserNo);
        }
    }

}
