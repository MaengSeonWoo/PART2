package com.talk.app.common.service;

public class LoginException extends Exception{
	public LoginException() {
		super("sessionError");
	}
}