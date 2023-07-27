package com.example.demo.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.ForgetRequest;
import com.example.demo.Dto.LoginRequest;
import com.example.demo.Dto.LogoutRequest;
import com.example.demo.Dto.RegistrationRequest;
import com.example.demo.Service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;


//在swagger上命名及描述
@Tag(name = "會員登入", description = "登入介面")
@RestController
public class LoginController {
	
	
	private UserService userService;
	
	//登入(輸入帳號、密碼) (對userService發出請求)
	@PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
		String account = loginRequest.getUserAccount();
        String password = loginRequest.getUserPassword();
        String token = userService.login(account, password);
        return token;
    }
	
	//註冊(輸入帳號、密碼、確認密碼、姓名、手機、mail、角色) (對userService發出請求)
	@PostMapping("/register")
	public String register(@RequestBody RegistrationRequest registrationRequest) {
		String Account = registrationRequest.getUserAccount();
		String Password = registrationRequest.getUserPassword();
		String confirmPassword = registrationRequest.getConfirmPassword();
		String Name = registrationRequest.getUserName();
		int Phone = registrationRequest.getUserPhone();
		String Email = registrationRequest.getUserEmail();
		int role = registrationRequest.getRole();
		return userService.register(Account, Password, confirmPassword, Name, Phone, Email, role);
	}
	
	//忘記密碼(輸入mail、新密碼、確認新密碼) (對userService發出請求)
	@PutMapping("/forget")
	public String forget(@RequestBody ForgetRequest forgetRequest) {
		String Email = forgetRequest.getUserEmail();
		String Password = forgetRequest.getUserPassword();
		String confirmPassword = forgetRequest.getConfirmPassword();
		
		return userService.forget(Email, Password, confirmPassword);
	}
	
	//登出(輸入帳號) (對userService發出請求)
	@PostMapping("/user/logout")
	public String logout(@RequestBody LogoutRequest logoutRequest) {
		String userAccount = logoutRequest.getAccount();
		return userService.logout(userAccount);
	}
	
}
