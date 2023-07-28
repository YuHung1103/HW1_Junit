package com.example.demo.Entity;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	@ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
	private List<Book> books = new ArrayList<>();
}
