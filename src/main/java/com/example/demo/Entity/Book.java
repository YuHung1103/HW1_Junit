package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//對應到指到的table
@Getter
@Setter
@Entity
@Table(name="books")
public class Book {
	
	@Id
	//要求資料庫自動生成主鍵
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;
	@Column(name="bookName")
	private String bookName;
	@Column(name="author")
	private String author;
	@Column(name="summary")
	private String summary;
	@Column(name="pricing")
	private int pricing;
	@Column(name="sellingPrice")
	private int sellingPrice;
	
}
