package com.talk.app.chatbot.service;

import lombok.Data;

@Data
public class Question {
	private int questionId;
    private String categoryName;
    private String initialQuestion;
    private int initialStepId;
}
