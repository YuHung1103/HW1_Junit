package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//對應到指到的table
@Getter
@Setter
@Entity
@Table(name="blacklist")
public class Blacklist {
	
	@Id
	@Column(name="account")
	private String account;
	
}
