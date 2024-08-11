package com.talk.app.chatbot.service.impl;

import java.util.List;

import com.talk.app.chatbot.mapper.FAQMapper;
import com.talk.app.chatbot.service.FAQService;
import com.talk.app.chatbot.service.FAQVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {
	
	private final FAQMapper faqMapper;

	@Override
	public FAQVO getFAQById(Long faqId) {
		return faqMapper.findById(faqId);
	}

	@Override
	public List<FAQVO> searchFAQByKeyword(List<String> keywords) {
		return faqMapper.findByKeywords(keywords);
	}

	@Override
	public List<FAQVO> getAllFAQs() {
		return faqMapper.findAll();
	}

	@Override
	public void createFAQ(FAQVO faq) {
		faqMapper.insertFAQ(faq);
		
	}

	@Override
	public void updateFAQ(FAQVO faq) {
		faqMapper.updateFAQ(faq);
		
	}

	@Override
	public void deleteFAQ(Long faqId) {
		faqMapper.deleteFAQ(faqId);
		
	}

}
