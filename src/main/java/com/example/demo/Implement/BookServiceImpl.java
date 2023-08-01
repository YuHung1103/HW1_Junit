package com.example.demo.Implement;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dao.AuthorRepository;
import com.example.demo.Dao.BookRepository;
import com.example.demo.Entity.Author;
import com.example.demo.Entity.Book;
import com.example.demo.Service.BookService;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	
	//取得所有book資料(對bookRepository操作)
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
	
	//取得指定book資料(對bookRepository操作)
	public Book getBook(int Id) {
		return bookRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
	}
	
	//新增資料(對bookRepository操作)
	public String createBook(String bookName, String summary, int pricing,
			int sellingPrice, String[] authorName) {
		Book book = new Book();
		book.setBookName(bookName);
		book.setSummary(summary);
		book.setPricing(pricing);
		book.setSellingPrice(sellingPrice);
		//有沒有輸入到相同的作者名稱
		if(HaveSameName(authorName)) {
			return "Have the same author name";
		};
		//查看作者table中，有沒有這位作者，如果有，就加入到陣列裡
		if(authorNotInTable(authorName, book)) {
			return "Not Found This Author";
		};
		bookRepository.save(book);
		return "Success";
	}
	
	//更新資料(對bookRepository操作) 先找有沒有此ID，再把新的資料set進舊的資料
	public String updateBook(int Id, String bookName, String summary, 
			int pricing, int sellingPrice, String[] authorName) {
		Book book = bookRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
		//有沒有輸入到相同的作者名稱
		if(HaveSameName(authorName)) {
			return "Have the same author name";
		};
		//清除作者跟書的關聯
		clearRelation(book);
	    //查看作者table中，有沒有這位作者，如果有，就加入到陣列裡
		if(authorNotInTable(authorName, book)) {
			return "Not Found This Author";
		};
		book.setBookName(bookName);
		book.setSummary(summary);
		book.setPricing(pricing);
		book.setSellingPrice(sellingPrice);
		bookRepository.save(book);
		return "Success";
	}
	
	//刪除資料(對bookRepository操作) 先找有沒有此ID，然後再做刪除
	public String deleteBook(int Id) {
		Book book = bookRepository.findById(Id).orElse(null);
		if(book==null) {
			return "Not Found";
		}
		//清除作者跟書的關聯
		clearRelation(book);
		bookRepository.deleteById(Id);
		return "Success";
	}
	
	//有沒有輸入到相同的作者名稱
	private Boolean HaveSameName(String[] authorName) {
		for(int i=0;i<authorName.length-1;i++) {
			for(int j=1;j<authorName.length;j++) {
				if(authorName[i].equals(authorName[j])){
					return true;
				}
			}
		}
		return false;
	}
	//清空所有跟本書有關的作者
	private void clearRelation(Book book) {
		List<Author> authors = book.getAuthors();
		for (Author author : authors) {
	        author.getBooks().remove(book);
	    }
	    //清空這本書的作者列表
	    authors.clear();
	}
	//查看作者table中，有沒有這位作者
	private Boolean authorNotInTable(String[] authorName, Book book) {
		for(int i=0;i<authorName.length;i++) {
			Author existAuthor = authorRepository.findByAuthorName(authorName[i]);
			if(existAuthor==null) {
				return true;
			}
			book.getAuthors().add(existAuthor);
			//existAuthor.getBooks().add(book);
		}
		return false;
	}
    
}
