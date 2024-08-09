package com.talk.app.chatbot.service;

import java.util.List;

public interface FAQService {

	public FAQVO getFAQById(Long faqId);
	
	public List<FAQVO> searchFAQByKeyword(String keyword);
	
	public List<FAQVO> getAllFAQs();
	
	public void createFAQ(FAQVO faq);
	
	public void updateFAQ(FAQVO faq);
	
	public void deleteFAQ(Long faqId);
}
