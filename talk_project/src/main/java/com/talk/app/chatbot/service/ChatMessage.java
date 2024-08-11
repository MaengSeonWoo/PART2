package com.talk.app.chatbot.service;

import lombok.Data;

@Data
public class ChatMessage {
	// 메시지를 보낸 사람의 이름
    private String sender;

    // 메시지의 내용
    private String content;

    // 메시지의 타입 (CHAT, JOIN, LEAVE)
    private MessageType type;
    
    // 채팅방 번호
    private String roomId;

    // MessageType 열거형: 메시지의 종류를 나타냄 (채팅 메시지, 사용자의 접속, 사용자의 퇴장)
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    public ChatMessage() {
    }

    public ChatMessage(String sender, String content, MessageType type, String roomId) {
		this.sender = sender;
		this.content = content;
		this.type = type;
		this.roomId = roomId;
	}

}
