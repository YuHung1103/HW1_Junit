package com.example.demo.Dto;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

	private int bookId;
	private String bookName;
	private String summary;
	private int pricing;
	private int sellingPrice;
	private Set<String> authorName = new HashSet<>();
}
