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
@Table(name="users")
public class User {
	
	@Id
	//要求資料庫自動生成主鍵
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userId")
	private int userId;
	@Column(name="userAccount")
	private String userAccount;
	@Column(name="userPassword")
	private String userPassword;
	@Column(name="userName")
	private String userName;
	@Column(name="userPhone")
	private int userPhone;
	@Column(name="userEmail")
	private String userEmail;
	
	@JsonIgnoreProperties(value = "users")
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_role", 
		joinColumns = @JoinColumn(name = "userId"), 
		inverseJoinColumns = @JoinColumn(name = "roleId"))
	private List<Role> roles = new ArrayList<>();
}
