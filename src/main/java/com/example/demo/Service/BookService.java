package com.example.demo.Service;

import java.util.List;

import com.example.demo.Dto.BookDto;

public interface BookService {
	
//	public List<Book> getAllBooks();
//	
//	public Book getBook(int Id);
//	
//	public Book createBook(Book NewBook);
//	
//	public Book updateBook(int Id, Book updatedBook);
//	
//	public String deleteBook(int Id);
	
	public String addBook(String bookName, String summary, int pricing,
			int sellingPrice, String authorName);
	public BookDto getOneBook(int bookId);
	public List<BookDto> getAllBooksAndAuthors(); 
}
