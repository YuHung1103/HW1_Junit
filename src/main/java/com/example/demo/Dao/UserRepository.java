package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.User;

//存放users的Repository，裡面放了一些對資料庫操作的功能
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUserAccount(String userAccount);

	User findByUserEmail(String userEmail);
}
