package com.talk.app.common.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class CommonUtil {
	public static String getUserId()   {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   // if(principal == null) throw new LoginException(); 
	    UserDetails userDetails = (UserDetails)principal;
	    String username = userDetails.getUsername();
	    
	    return username;
	}
}
