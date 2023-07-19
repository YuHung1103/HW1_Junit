package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

//從json轉換為java，所需用到的物件(用於登出)
@Getter
@Setter
public class LogoutRequest {

	private String account;
	
}
