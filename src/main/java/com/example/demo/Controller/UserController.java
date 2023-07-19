package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;


//在swagger上命名及描述
@Tag(name = "會員管理", description = "可控制會員資料")
@RestController
public class UserController {
	
	private UserService userService;
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	//取得所有user資料(對userService發出請求)
	@GetMapping("/user")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	
	//取得指定user資料(對userService發出請求)
	@GetMapping("/user/{Id}")
	public User getUser(@PathVariable int Id) {
		return userService.getUser(Id);
	}
	
	//新增資料(對userService發出請求)
	@PostMapping("/user")
	public User createUser(@RequestBody User NewUser) {
		return userService.createUser(NewUser);
	}
	
	//更新資料(對userService發出請求)
	@PutMapping("/user/{Id}")
	public User updateUser(@PathVariable int Id, @RequestBody User updatedUser) {
		return userService.updateUser(Id, updatedUser);
	}
	
	//刪除資料(對userService發出請求)
	@DeleteMapping("/user/{Id}")
	public String deteleUser(@PathVariable("Id") int Id) {
		return userService.deleteUser(Id);
	}
}
