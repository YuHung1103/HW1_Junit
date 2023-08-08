package com.example.demo.Service;

import com.example.demo.Dao.FatherRepository;
import com.example.demo.Entity.Kid;

import java.util.List;

public interface KidService {
    List<Kid> getAllKids();
    Kid getKid(int Id);
    String createKid(String kidName, String fatherName);
    String updateKid(int Id, String kidName, String fatherName);
    String deleteBook(int Id);

}
