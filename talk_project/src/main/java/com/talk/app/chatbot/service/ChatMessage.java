package com.talk.app.chatbot.service;

public class ChatMessage {
	// 메시지를 보낸 사람의 이름
    private String sender;

    // 메시지의 내용
    private String content;

    // 메시지의 타입 (CHAT, JOIN, LEAVE)
    private MessageType type;

    // MessageType 열거형: 메시지의 종류를 나타냄 (채팅 메시지, 사용자의 접속, 사용자의 퇴장)
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    public ChatMessage() {
    }

    public ChatMessage(String sender, String content, MessageType type) {
		this.sender = sender;
		this.content = content;
		this.type = type;
	}

	// Getter와 Setter 메서드: 외부에서 변수에 접근하고 수정할 수 있게 함
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
