package com.example.demo.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dao.BookRepository;
import com.example.demo.Entity.Book;
import com.example.demo.Service.BookService;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	//取得所有book資料(對bookRepository操作)
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
	
	//取得指定book資料(對bookRepository操作)
	public Book getBook(int Id) {
		return bookRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
	}
	
	//新增資料(對bookRepository操作)
	public Book createBook(Book NewBook) {
		return bookRepository.save(NewBook);
	}
	
	//更新資料(對bookRepository操作) 先找有沒有此ID，再把新的資料set進舊的資料
	public Book updateBook(int Id, Book updatedBook) {
		Book book = bookRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
		book.setBookName(updatedBook.getBookName());
		book.setAuthor(updatedBook.getAuthor());
		book.setSummary(updatedBook.getSummary());
		book.setPricing(updatedBook.getPricing());
		book.setSellingPrice(updatedBook.getSellingPrice());
		return bookRepository.save(book);
	}
	
	//刪除資料(對bookRepository操作) 先找有沒有此ID，然後再做刪除
	public String deleteBook(int Id) {
		Boolean exists = bookRepository.existsById(Id);
		if(!exists) {
			return "Not Found";
		}
		bookRepository.deleteById(Id);
		return "Success";
	}
}
