package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="role")
public class Role {
	
	@Id
	//要求資料庫自動生成主鍵
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	@Column(name="role")
	private String role;
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private List<User> users = new ArrayList<>();
}
