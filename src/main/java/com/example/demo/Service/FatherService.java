package com.example.demo.Service;

import com.example.demo.Entity.Father;
import com.example.demo.Entity.Kid;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface FatherService {
    List<Father> getAllFathers();
    Father getFather(int Id);
    String createFather(String fatherName, String[] kidName);
    String updateFather(int Id, String fatherName,  String[] kidName);
    String deleteFather(int Id);
}
