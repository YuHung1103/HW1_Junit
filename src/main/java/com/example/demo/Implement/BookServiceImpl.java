package com.example.demo.Implement;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dao.AuthorRepository;
import com.example.demo.Dao.BookRepository;
import com.example.demo.Dto.BookDto;
import com.example.demo.Entity.Author;
import com.example.demo.Entity.Book;
import com.example.demo.Service.BookService;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	
	public String addBook(String bookName, String summary, int pricing,
			int sellingPrice, String authorName) {
		Author existAuthor = authorRepository.findByAuthorName(authorName);
		if(existAuthor == null) {
			return "Please enter author information first";
		}
		Book book = new Book();
		book.setBookName(bookName);
		book.setSummary(summary);
		book.setPricing(pricing);
		book.setSellingPrice(sellingPrice);
		book.getAuthor().add(existAuthor);
		existAuthor.getBook().add(book);
		bookRepository.save(book);
		
		return "Success";
	}
	public String deleteBook(int Id) {
		Boolean exists = bookRepository.existsById(Id);
		if(!exists) {
			return "Not Found";
		}
		bookRepository.deleteById(Id);
		return "Success";
	}
	public List<BookDto> getAllBooksAndAuthors() {
        List<Object[]> results = bookRepository.findAllBooksAndAuthors();

        List<BookDto> dtos = new ArrayList<>();
        for (Object[] result : results) {
            Book book = (Book) result[0];
            String authorName = (String) result[1];

            //使用Stream API來尋找是否已經有相同bookID
            BookDto dto = dtos.stream()
            	//相同的話，直接取得該物件
                .filter(d -> d.getBookId() == (book.getBookId()))
                .findFirst()
                .orElseGet(() -> {
                	BookDto newDto = new BookDto();
                    newDto.setBookId(book.getBookId());
                    newDto.setBookName(book.getBookName());
                    newDto.setSummary(book.getSummary());
                    newDto.setPricing(book.getPricing());
                    newDto.setSellingPrice(book.getSellingPrice());
                    return newDto;
                });
            dto.getAuthorName().add(authorName);
            dtos.add(dto);
        }
        return dtos;
    }
	public BookDto getOneBook(int bookId) {
		Book book = bookRepository.findOneBookAndAuthors(bookId);
        if (book == null) {
            return null;
        }
        //建立BookDto物件並轉換book資料
        BookDto dto = new BookDto();
        dto.setBookId(book.getBookId());
        dto.setBookName(book.getBookName());
        dto.setSummary(book.getSummary());
        dto.setPricing(book.getPricing());
        dto.setSellingPrice(book.getSellingPrice());
        //取得book的作者名稱
        Set<String> authorNames = book.getAuthor().stream()
            .map(Author::getAuthorName)
            .collect(Collectors.toSet());
        dto.setAuthorName(authorNames);

        return dto;
	}
	
//	//取得所有book資料(對bookRepository操作)
//	public List<Book> getAllBooks(){
//		return bookRepository.findAll();
//	}
//	
//	//取得指定book資料(對bookRepository操作)
//	public Book getBook(int Id) {
//		return bookRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
//	}
//	
//	//新增資料(對bookRepository操作)
//	public Book createBook(Book NewBook) {
//		return bookRepository.save(NewBook);
//	}
//	
//	//更新資料(對bookRepository操作) 先找有沒有此ID，再把新的資料set進舊的資料
//	public Book updateBook(int Id, Book updatedBook) {
//		Book book = bookRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
//		book.setBookName(updatedBook.getBookName());
//		book.setAuthor(updatedBook.getAuthor());
//		book.setSummary(updatedBook.getSummary());
//		book.setPricing(updatedBook.getPricing());
//		book.setSellingPrice(updatedBook.getSellingPrice());
//		return bookRepository.save(book);
//	}
//	
//	//刪除資料(對bookRepository操作) 先找有沒有此ID，然後再做刪除
//	public String deleteBook(int Id) {
//		Boolean exists = bookRepository.existsById(Id);
//		if(!exists) {
//			return "Not Found";
//		}
//		bookRepository.deleteById(Id);
//		return "Success";
//	}
}
