package com.talk.app.chatbot.service;

public interface ChatbotService {
	
//	public String getResponse(String message, String roomId);
	void initializeRoom(String roomId);
    void endRoom(String roomId);
    String processMessage(ChatMessage message);
}
