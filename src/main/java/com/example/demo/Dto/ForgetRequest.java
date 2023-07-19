package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

//從json轉換為java，所需用到的物件(用於忘記密碼)
@Getter
@Setter
public class ForgetRequest {

	private String userEmail;
	private String userPassword;
	private String confirmPassword;
	
}
