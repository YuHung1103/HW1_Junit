package com.example.demo.Unit;

import com.example.demo.Dao.AuthorRepository;
import com.example.demo.Dao.BookRepository;
import com.example.demo.Entity.Author;
import com.example.demo.Entity.Book;
import com.example.demo.Implement.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BookUnitTest {

    //會把下列兩個有使用Mock註解的物件，注入進這個class中
    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    //創建虛擬物件
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;

    @Test
    public void testCreateBook(){
        //創建虛擬資料
        Author mockAuthor1 = new Author();
        mockAuthor1.setAuthorId(1);
        mockAuthor1.setAuthorName("Author1");
        mockAuthor1.setBirthday(LocalDate.of(1999, 9, 9));

        Author mockAuthor2 = new Author();
        mockAuthor2.setAuthorId(1);
        mockAuthor2.setAuthorName("Author2");
        mockAuthor2.setBirthday(LocalDate.of(2000, 10, 10));

        //遇到...就回傳...
        when(authorRepository.findByAuthorName(eq("Author1"))).thenReturn(mockAuthor1);
        when(authorRepository.findByAuthorName(eq("Author2"))).thenReturn(mockAuthor2);

        //使用bookServiceImpl的實際方法，並存入回傳值
        String[] authors = {"Author1","Author2"};
        String result = bookServiceImpl.createBook("test", "testSummary", 500, 450, authors);

        //斷言
        assertEquals("Success", result);
    }

    @Test
    public void testGetAllBooks(){
        //創建虛擬資料
        List<Author> mockAuthors= new ArrayList<>();

        Author mockAuthor = new Author();
        mockAuthor.setAuthorId(1);
        mockAuthor.setAuthorName("Author1");
        mockAuthor.setBirthday(LocalDate.of(1999, 9, 9));
        mockAuthors.add(mockAuthor);

        List<Book> mockBooks = new ArrayList<>();

        Book book1 = new Book();
        book1.setBookName("test1");
        book1.setSummary("summary1");
        book1.setPricing(500);
        book1.setSellingPrice(450);
        book1.setAuthors(mockAuthors);
        mockBooks.add(book1);

        Book book2 = new Book();
        book2.setBookName("test2");
        book2.setSummary("summary2");
        book2.setPricing(400);
        book2.setSellingPrice(350);
        book2.setAuthors(mockAuthors);
        mockBooks.add(book2);

        //遇到...就回傳...
        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> result = bookServiceImpl.getAllBooks();

        //斷言
        assertEquals(mockBooks.size(), result.size());
        assertEquals(mockBooks.get(0).getBookName(), result.get(0).getBookName());
        assertEquals(mockBooks.get(1).getBookName(), result.get(1).getBookName());

        //驗證方法有沒有被使用
        verify(bookRepository).findAll();
    }

    @Test
    public void testGetBook(){
        //創建虛擬資料
        List<Author> mockAuthors= new ArrayList<>();

        Author mockAuthor = new Author();
        mockAuthor.setAuthorId(1);
        mockAuthor.setAuthorName("Author");
        mockAuthor.setBirthday(LocalDate.of(1999, 9, 9));
        mockAuthors.add(mockAuthor);

        Book book = new Book();
        book.setBookName("test");
        book.setSummary("summary");
        book.setPricing(500);
        book.setSellingPrice(450);
        book.setAuthors(mockAuthors);

        //遇到...就回傳...
        when(bookRepository.findById(eq(1))).thenReturn(Optional.of(book));

        //使用bookServiceImpl的實際方法，並存入回傳值
        Book result = bookServiceImpl.getBook(1);

        //斷言
        assertEquals(book.getBookName(), result.getBookName());

        //驗證方法有沒有被使用
        verify(bookRepository).findById(eq(1));
    }

    @Test
    public void testUpdateBook(){
        //創建虛擬資料
        List<Author> mockAuthors= new ArrayList<>();

        Author mockAuthor = new Author();
        mockAuthor.setAuthorId(1);
        mockAuthor.setAuthorName("Author");
        mockAuthor.setBirthday(LocalDate.of(1999, 9, 9));
        mockAuthors.add(mockAuthor);

        Book book = new Book();
        book.setBookId(1);
        book.setBookName("test");
        book.setSummary("summary");
        book.setPricing(500);
        book.setSellingPrice(450);
        book.setAuthors(mockAuthors);

        //遇到...就回傳...
        when(bookRepository.findById(eq(1))).thenReturn(Optional.of(book));
        when(authorRepository.findByAuthorName(eq("Author"))).thenReturn(mockAuthor);

        //使用bookServiceImpl的實際方法，並存入回傳值
        String[] authors = {"Author"};
        String result = bookServiceImpl.updateBook(1,"aa", "aaSummary", 100, 50, authors);

        //斷言
        assertEquals("Success", result);

        //驗證方法有沒有被使用
        verify(bookRepository).findById(eq(1));
        verify(authorRepository).findByAuthorName(eq("Author"));
        verify(bookRepository).save(book);
    }

    @Test
    public void testDeleteBook(){
        //創建虛擬資料
        List<Author> mockAuthors= new ArrayList<>();

        Author mockAuthor = new Author();
        mockAuthor.setAuthorId(1);
        mockAuthor.setAuthorName("Author");
        mockAuthor.setBirthday(LocalDate.of(1999, 9, 9));
        mockAuthors.add(mockAuthor);

        Book book = new Book();
        book.setBookId(1);
        book.setBookName("test");
        book.setSummary("summary");
        book.setPricing(500);
        book.setSellingPrice(450);
        book.setAuthors(mockAuthors);

        //遇到...就回傳...
        when(bookRepository.findById(eq(1))).thenReturn(Optional.of(book));

        //使用bookServiceImpl的實際方法，並存入回傳值
        String result = bookServiceImpl.deleteBook(1);

        //斷言
        assertEquals("Success", result);

        //驗證方法有沒有被使用
        verify(bookRepository).findById(eq(1));
        verify(bookRepository).deleteById(eq(1));
    }
}
