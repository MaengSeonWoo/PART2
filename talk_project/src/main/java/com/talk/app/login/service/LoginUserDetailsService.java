package com.talk.app.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.talk.app.login.mapper.UserMapper;

@Service
public class LoginUserDetailsService implements UserDetailsService{
	private UserMapper userMapper;
	
	@Autowired
	LoginUserDetailsService(UserMapper userMapper){
		this.userMapper = userMapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO userVO = userMapper.getUserInfo(username);
		System.out.println("================="+userVO);
		if(userVO == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new LoginUserVO(userVO);
	}
		
}
