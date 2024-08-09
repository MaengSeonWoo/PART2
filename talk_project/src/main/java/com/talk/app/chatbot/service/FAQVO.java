package com.talk.app.chatbot.service;

import java.util.Date;

import lombok.Data;

@Data
public class FAQVO {
    private Long faqId;
    private String question;
    private String answer;
    private Date createDate;
    private Date updateDate;
    private String keyword;
}