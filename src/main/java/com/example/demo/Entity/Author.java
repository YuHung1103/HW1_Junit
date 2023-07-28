package com.example.demo.Entity;

import java.util.Set;
import java.util.HashSet;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="authors")
public class Author {
	
	@Id
	//要求資料庫自動生成主鍵
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int authorId;
	@Column(name="authorName")
	private String authorName;
	@Column(name="birthday")
	private LocalDate birthday;
	@ManyToMany(mappedBy = "author")
	private Set<Book> book = new HashSet<>();
}
