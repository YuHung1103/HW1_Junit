package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookRequest {
	
	private String bookName;
	private String summary;
	private int pricing;
	private int sellingPrice;
	private String authorName;
	
}
