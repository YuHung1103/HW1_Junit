package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{

	Author findByAuthorName(String authorName);

}
