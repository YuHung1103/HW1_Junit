package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

	private String userAccount;
	private String userPassword;
	private String userName;
	private int userPhone;
	private String userEmail;
	private String[] role;
	
}
