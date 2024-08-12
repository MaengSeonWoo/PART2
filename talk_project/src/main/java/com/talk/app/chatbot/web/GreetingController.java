package com.talk.app.chatbot.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import com.talk.app.chatbot.service.Greeting;
import com.talk.app.chatbot.service.HelloMessage;

@Controller
public class GreetingController {
	
	@GetMapping("/chatbot3")
	public String chatbot() {
		return "chatbot/index";
	}
	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(500);
		Greeting greeting = new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
		return greeting;
	  }
}
