package com.talk.app.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.talk.app.login.mapper.CoUserMapper;

//@Service
public class LoginCoUserDetailsService implements UserDetailsService{
	
	private CoUserMapper couserMapper;
	
	@Autowired
	LoginCoUserDetailsService(CoUserMapper couserMapper){
		this.couserMapper = couserMapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CoUserVO couserVO = couserMapper.getCoUserInfo(username);
		
		if(couserVO == null) {
			throw new UsernameNotFoundException(username);
		}
		return new LoginCoUserVO(couserVO);
	}
	
}
