package com.example.demo.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.AddBookRequest;
import com.example.demo.Dto.UpdateBookRequest;
import com.example.demo.Entity.Book;
import com.example.demo.Service.BookService;

import io.swagger.v3.oas.annotations.tags.Tag;

//在swagger上命名及描述
@Tag(name = "圖書管理", description = "可控制圖書資料")
@RestController
public class BookController {

	@Autowired
	private BookService bookService;
	
	//取得所有book資料(對bookService發出請求)
	@GetMapping("/book")
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	
	//取得指定book的資料(對bookService發出請求)
	@GetMapping("/book/{Id}")
	public Book getBook(@PathVariable int Id) {
		return bookService.getBook(Id);
	}
	
	//新增資料(對bookService發出請求)
	@PostMapping("/book")
	public String createBook(@RequestBody AddBookRequest addBookRequest) {
		String bookName = addBookRequest.getBookName();
		String summary = addBookRequest.getSummary();
		int pricing = addBookRequest.getPricing();
		int sellingPrice = addBookRequest.getSellingPrice();
		String[] authorName = addBookRequest.getAuthorName();
		return bookService.createBook(bookName, summary, pricing, sellingPrice, authorName);
	}
	
	//更新資料(對bookService發出請求)
	@PutMapping("/book/{Id}")
	public String updateBook(@PathVariable int Id, @RequestBody UpdateBookRequest updateBookRquest) {
		String bookName = updateBookRquest.getBookName();
		String summary = updateBookRquest.getSummary();
		int pricing = updateBookRquest.getPricing();
		int sellingPrice = updateBookRquest.getSellingPrice();
		String[] authorName = updateBookRquest.getAuthorName();
		return bookService.updateBook(Id, bookName, summary, pricing, sellingPrice, authorName);
	}
	
	//刪除資料(對bookService發出請求)
	@DeleteMapping("/book/{Id}")
	public String deleteBook(@PathVariable int Id) {
		return bookService.deleteBook(Id);
	}
}
