package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Blacklist;

//存放blacklist的Repository，裡面放了一些對資料庫操作的功能
@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Integer>{

	Blacklist findByAccount(String userAccount);

	boolean existsByAccount(String userAccount);

	void deleteByAccount(String userAccount);

}
