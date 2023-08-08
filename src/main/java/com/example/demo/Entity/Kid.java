package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="kids")
public class Kid {

    @Id
    //要求資料庫自動生成主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kidId")
    private int kidId;
    @Column(name="kidName")
    private String kidName;
    @JsonIgnoreProperties(value = "kids")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "fathers_kids",
            joinColumns = @JoinColumn(name = "kidId"),
            inverseJoinColumns = @JoinColumn(name = "fatherId"))
    private Father father;
}
