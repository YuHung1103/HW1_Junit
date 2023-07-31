package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Book;

//存放books的Repository，裡面放了一些對資料庫操作的功能
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	
}
