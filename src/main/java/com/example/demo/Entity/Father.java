package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="fathers")
public class Father {

    @Id
    //要求資料庫自動生成主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fatherId")
    private int fatherId;
    @Column(name="fatherName")
    private String fatherName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "father")
    private List<Kid> kids = new ArrayList<>();
}
