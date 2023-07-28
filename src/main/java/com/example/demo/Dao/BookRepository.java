package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Book;

//存放books的Repository，裡面放了一些對資料庫操作的功能
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	
	//每個Object[]包含了Book物件和對應的AuthorName。
	//這種Query寫法就為明確，因為它已經指定了什麼類別及什麼欄位
	@Query("SELECT b, a.authorName FROM Book b JOIN b.author a")
    List<Object[]> findAllBooksAndAuthors();
    
    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE b.bookId = :bookId")
    Book findOneBookAndAuthors(@Param("bookId") int boookId);
}
