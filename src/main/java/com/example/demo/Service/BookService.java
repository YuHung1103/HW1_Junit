package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.Book;

public interface BookService {
	
	public List<Book> getAllBooks();
	
	public Book getBook(int Id);
	
	public String createBook(String bookName, String summary, int pricing,
			int sellingPrice, String[] authorName);
	
	public String updateBook(int Id, String bookName, String summary, 
			int pricing, int sellingPrice, String[] authorName);
	
	public String deleteBook(int Id);
	
//	public String addBook(String bookName, String summary, int pricing,
//			int sellingPrice, String authorName);
//	public BookDto getOneBook(int bookId);
//	public List<BookDto> getAllBooksAndAuthors(); 
}
