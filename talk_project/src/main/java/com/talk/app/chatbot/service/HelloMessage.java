package com.talk.app.chatbot.service;

public class HelloMessage {
    private String name;

    // 기본 생성자 추가
    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}