package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.Book;

public interface BookService {
	
	List<Book> getAllBooks();
	
	Book getBook(int Id);
	
	String createBook(String bookName, String summary, int pricing,
			int sellingPrice, String[] authorName);
	
	String updateBook(int Id, String bookName, String summary,
			int pricing, int sellingPrice, String[] authorName);
	
	String deleteBook(int Id);
	
}
