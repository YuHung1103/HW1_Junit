package com.example.demo.Dao;

import com.example.demo.Entity.Kid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KidRepository extends JpaRepository<Kid, Integer> {
    Kid findBykidName(String kidName);
}
