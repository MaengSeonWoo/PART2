package com.talk.app.chatbot.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.chatbot.service.ChatMessage;
import com.talk.app.chatbot.service.ChatMessage.MessageType;
import com.talk.app.chatbot.service.ChatbotService;

import lombok.RequiredArgsConstructor;

/*
 * 작성자 : 김진형
 * 작성일자 : 2024-08-10
 * 챗봇 : 챗봇 방 생성, 챗봇 방 폐쇄, 메시지 보내기
 * */
@Controller
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatController {

	private final ChatbotService chatbotService;
	private final SimpMessagingTemplate messagingTemplate;
	private final ConcurrentHashMap<String, String> rooms = new ConcurrentHashMap<>();

	@GetMapping("/chatbot2")
	public String chatbot() {
		return "chatbot/chattest";
	}

	@PostMapping("/createRoom")
	@ResponseBody
	public Map<String, String> createRoom() {
		String roomId = UUID.randomUUID().toString();
		rooms.put(roomId, roomId);
		Map<String, String> response = new HashMap<>();
		response.put("roomId", roomId);
		return response;
	}

	@PostMapping("/endRoom/{roomId}")
	@ResponseBody
	public ResponseEntity<String> endRoom(@PathVariable String roomId) {
		if (rooms.remove(roomId) != null) {
			return ResponseEntity.ok("Room ended successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
		}
	}

	@MessageMapping("/chat.sendMessage")
	public void sendMessage(ChatMessage message) {
		String roomId = message.getRoomId();
		String response = chatbotService.getResponse(message.getContent());
		ChatMessage botMessage = new ChatMessage("Chatbot", response, MessageType.CHAT, roomId);
		messagingTemplate.convertAndSend("/topic/" + roomId, botMessage);
	}

//	@MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(ChatMessage message) {
//        String response = chatbotService.getResponse(message.getContent());
//        return new ChatMessage(message.getSender(), response, MessageType.CHAT);
//    }

//	@MessageMapping("/chat.addUser")
//	public void addUser(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
//		String roomId = message.getRoomId();
//		headerAccessor.getSessionAttributes().put("roomId", roomId);
//		messagingTemplate.convertAndSend("/topic/" + roomId,
//				new ChatMessage(message.getSender(), "님이 입장하셨습니다.", MessageType.JOIN, roomId));
//	}
//
//	@MessageMapping("/chat.leaveUser")
//	public void leaveUser(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
//		String roomId = message.getRoomId();
//		messagingTemplate.convertAndSend("/topic/" + roomId,
//				new ChatMessage(message.getSender(), "님이 퇴장하셨습니다.", MessageType.LEAVE, roomId));
//		headerAccessor.getSessionAttributes().remove("roomId");
//	}

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
