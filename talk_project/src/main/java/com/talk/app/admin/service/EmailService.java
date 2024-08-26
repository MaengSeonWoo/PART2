package com.talk.app.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.talk.app.admin.mapper.WelfareMapper;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;


@Service
public class EmailService {
	@Value("${mailgun.api.key}")
    private static String apiKey;

	  @Value("${mailgun.domain.name}")
	    private static String domainName;
	  
	  
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
    
    
  	public JsonNode sendEmail() throws UnirestException {
  		HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "sandbox3c725b0cf1e14d22b2b01773ce3c9fe1.mailgun.org" + "/messages")
  	  			.basicAuth("api", "2b91eb47-df0104d2")
  	  			.queryString("from", "Excited User <syj6180@naver.com>")
  	  			.queryString("to", "syj6180@naver.com")
  	  			.queryString("subject", "hello")
  	  			.queryString("text", "testing")
  	  			.asJson();
  	  		return request.getBody();
  	}
    
    
    
    

}
