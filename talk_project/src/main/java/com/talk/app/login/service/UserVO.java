package com.talk.app.login.service;

import lombok.Data;

@Data
public class UserVO {
	private Integer userNo; 	//ȸ����ȣ
	private String userName;	//�̸�
	private String userId;		//���̵�
	private String userPw;		//��й�ȣ
	private String Tel;			//����ó
	private String postNo;		//�����ȣ
	private String addr;		//�⺻�ּ�
	private String detailAddr;	//���ּ�
	private String email;		//�̸���
	private String birth;		//�������
	private String household;	//������Ȳ
	private String gender;		//����
	private String authority;	//����
}
