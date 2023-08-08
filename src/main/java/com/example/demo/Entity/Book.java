package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//對應到指到的table
@Getter
@Setter
@Entity
@Table(name="books")
public class Book {

    @Id
    //要求資料庫自動生成主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookId")
    private int bookId;
    @Column(name="bookName")
    private String bookName;
    @Column(name="summary")
    private String summary;
    @Column(name="pricing")
    private int pricing;
    @Column(name="sellingPrice")
    private int sellingPrice;

    @JsonIgnoreProperties(value = "books")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "authorId"))
    private List<Author> authors = new ArrayList<>();
}
