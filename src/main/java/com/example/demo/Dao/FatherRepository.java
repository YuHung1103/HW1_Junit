package com.example.demo.Dao;

import com.example.demo.Entity.Father;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FatherRepository extends JpaRepository<Father, Integer> {

    Father findByFatherName(String fatherName);
}
