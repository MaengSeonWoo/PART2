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

	@PostMapping("/createRoom")
	@ResponseBody
	public Map<String, String> createRoom() {
		String roomId = UUID.randomUUID().toString();
        rooms.put(roomId, roomId);
        chatbotService.initializeRoom(roomId);
        Map<String, String> response = new HashMap<>();
        response.put("roomId", roomId);
        return response;
	}

	@PostMapping("/endRoom/{roomId}")
	@ResponseBody
	public ResponseEntity<String> endRoom(@PathVariable String roomId) {
		if (rooms.remove(roomId) != null) {
            chatbotService.endRoom(roomId);
            return ResponseEntity.ok("Room ended successfully");
        } else {
            return ResponseEntity.status(404).body("Room not found");
        }
    }

	@MessageMapping("/chat.sendMessage")
	public void sendMessage(ChatMessage message) {
		String roomId = message.getRoomId();
        String response = chatbotService.processMessage(message);

        ChatMessage botMessage = new ChatMessage("Chatbot", response, message.getType(), roomId);
        messagingTemplate.convertAndSend("/topic/" + roomId, botMessage);
    }
}
