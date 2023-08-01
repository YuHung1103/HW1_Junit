package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

//從json轉換為java，所需用到的物件(用於註冊)
@Getter
@Setter
public class RegistrationRequest {

	private String userAccount;
	private String userPassword;
	private String confirmPassword;
	private String userName;
	private int userPhone;
	private String userEmail;
	private String[] role;

}
