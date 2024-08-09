package com.talk.app.chatbot.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.talk.app.chatbot.service.ChatMessage;
import com.talk.app.chatbot.service.ChatMessage.MessageType;
import com.talk.app.chatbot.service.ChatbotService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	
	private final ChatbotService chatbotService;
	
	@GetMapping("/chatbot2")
	public String chatbot() {
		return "chatbot/chattest";
	}
	
	@MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {
        String response = chatbotService.getResponse(message.getContent());
        return new ChatMessage(message.getSender(), response, MessageType.CHAT);
    }
    
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage message) {
        return new ChatMessage(message.getSender(), "님이 입장하셨습니다.", MessageType.JOIN);
    }
	
//	// 메시지를 처리하는 메서드: 클라이언트가 "/app/chat.sendMessage"로 메시지를 보냈을 때 호출됨
//    @MessageMapping("/chat.sendMessage")
//    // @SendTo: 해당 메서드의 반환값이 "/topic/public" 경로로 전송됨
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(ChatMessage chatMessage) {
//        // 클라이언트로부터 받은 메시지를 그대로 반환하여, 다른 구독자들에게 브로드캐스트
//        return chatMessage;
//    }
//
//    // 사용자가 채팅에 참여할 때 호출되는 메서드: 클라이언트가 "/app/chat.addUser"로 메시지를 보냈을 때 호출됨
//    @MessageMapping("/chat.addUser")
//    // @SendTo: 해당 메서드의 반환값이 "/topic/public" 경로로 전송됨
//    @SendTo("/topic/public")
//    public ChatMessage addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//        // SimpMessageHeaderAccessor: WebSocket 세션의 속성에 접근할 수 있게 함
//        // 사용자의 이름을 세션에 저장
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        // 메시지 타입을 JOIN으로 설정하여, 다른 사용자들이 새로운 사용자가 참여했음을 알 수 있게 함
//        chatMessage.setType(ChatMessage.MessageType.JOIN);
//        return chatMessage;
//    }
}
