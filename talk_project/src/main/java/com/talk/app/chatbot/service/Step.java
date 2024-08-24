package com.talk.app.chatbot.service;

import lombok.Data;

@Data
public class Step {
	private int stepId;
    private int questionId;
    private String stepText;
    private int nextStepId;
    private String actionUrl;
}
