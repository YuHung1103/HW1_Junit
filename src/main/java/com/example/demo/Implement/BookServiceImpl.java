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
	
//	public String addBook(String bookName, String summary, int pricing,
//			int sellingPrice, String authorName) {
//		Author existAuthor = authorRepository.findByAuthorName(authorName);
//		if(existAuthor == null) {
//			return "Please enter author information first";
//		}
//		Book book = new Book();
//		book.setBookName(bookName);
//		book.setSummary(summary);
//		book.setPricing(pricing);
//		book.setSellingPrice(sellingPrice);
//		book.getAuthor().add(existAuthor);
//		existAuthor.getBook().add(book);
//		bookRepository.save(book);
//		
//		return "Success";
//	}
//	public String deleteBook(int Id) {
//		Book Book = bookRepository.findById(Id).orElseThrow();
//		if(Book==null) {
//			return "Not Found";
//		}
//		Author Author = authorRepository.findByAuthorName(Book.getBookName());
//		bookRepository.deleteById(Id);
//		Author.getBook().remove(Book);
//		return "Success";
//	}
//	public List<BookDto> getAllBooksAndAuthors() {
//        List<Object[]> results = bookRepository.findAllBooksAndAuthors();
//
//        List<BookDto> dtos = new ArrayList<>();
//        for (Object[] result : results) {
//            Book book = (Book) result[0];
//            String authorName = (String) result[1];
//
//            //使用Stream API來尋找是否已經有相同bookID
//            BookDto dto = dtos.stream()
//            	//相同的話，直接取得該物件
//                .filter(d -> d.getBookId() == (book.getBookId()))
//                .findFirst()
//                .orElseGet(() -> {
//                	BookDto newDto = new BookDto();
//                    newDto.setBookId(book.getBookId());
//                    newDto.setBookName(book.getBookName());
//                    newDto.setSummary(book.getSummary());
//                    newDto.setPricing(book.getPricing());
//                    newDto.setSellingPrice(book.getSellingPrice());
//                    return newDto;
//                });
//            dto.getAuthorName().add(authorName);
//            dtos.add(dto);
//        }
//        return dtos;
//    }
//	public BookDto getOneBook(int bookId) {
//		Book book = bookRepository.findOneBookAndAuthors(bookId);
//        if (book == null) {
//            return null;
//        }
//        //建立BookDto物件並轉換book資料
//        BookDto dto = new BookDto();
//        dto.setBookId(book.getBookId());
//        dto.setBookName(book.getBookName());
//        dto.setSummary(book.getSummary());
//        dto.setPricing(book.getPricing());
//        dto.setSellingPrice(book.getSellingPrice());
//        //取得book的作者名稱
//        Set<String> authorNames = book.getAuthor().stream()
//            .map(Author::getAuthorName)
//            .collect(Collectors.toSet());
//        dto.setAuthorName(authorNames);
//
//        return dto;
//	}
	
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
		for(int i=0;i<authorName.length;i++) {
			Author existAuthor = authorRepository.findByAuthorName(authorName[i]);
			if(existAuthor==null) {
				return "Not Found This Author";
			}
			book.getAuthors().add(existAuthor);
			existAuthor.getBooks().add(book);
		}
		bookRepository.save(book);
		return "Success";
	}
	
	//更新資料(對bookRepository操作) 先找有沒有此ID，再把新的資料set進舊的資料
	public String updateBook(int Id, String bookName, String summary, 
			int pricing, int sellingPrice, String[] authorName) {
		Book book = bookRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
		Iterator<Author> iterator = book.getAuthors().iterator();
		while(iterator.hasNext()) {
		    Author author = iterator.next();
		    author.getBooks().remove(book);
		    iterator.remove();
		}
		System.out.println(book.getAuthors().size()+"ok");
		for(int i=0;i<authorName.length;i++) {
			Author existAuthor = authorRepository.findByAuthorName(authorName[i]);
			if(existAuthor==null) {
				return "Not Found This Author";
			}
			book.getAuthors().add(existAuthor);
			existAuthor.getBooks().add(book);
		}
		book.setBookName(bookName);
		book.setSummary(summary);
		book.setPricing(pricing);
		book.setSellingPrice(sellingPrice);
		bookRepository.save(book);
		return "Success";
	}
	
	//刪除資料(對bookRepository操作) 先找有沒有此ID，然後再做刪除
	public String deleteBook(int Id) {
		Book book = bookRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
		if(book==null) {
			return "Not Found";
		}
		Iterator<Author> iterator = book.getAuthors().iterator();
		while(iterator.hasNext()) {
		    Author author = iterator.next();
		    author.getBooks().remove(book);
		    iterator.remove();
		}
		bookRepository.deleteById(Id);
		return "Success";
	}
}
