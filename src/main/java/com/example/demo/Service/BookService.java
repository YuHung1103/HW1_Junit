package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.Book;

public interface BookService {
	
	public List<Book> getAllBooks();
	
	public Book getBook(int Id);
	
	public Book createBook(Book NewBook);
	
	public Book updateBook(int Id, Book updatedBook);
	
	public String deleteBook(int Id);
}
