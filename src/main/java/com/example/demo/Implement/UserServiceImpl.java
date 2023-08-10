package com.example.demo.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Dao.RoleRepository;
import com.example.demo.Dao.UserRepository;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Jwt.JwtTokenGenerator;
import com.example.demo.Service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	//取得所有user資料(對userRepository操作)
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	//取得指定user資料(對userRepository操作)
	public User getUser(int Id){
		return userRepository.findById(Id)
				.orElseThrow(() -> new RuntimeException("Not Found"));
	}
	
	//新增資料(對userRepository操作)
	public String createUser(String userAccount, String userPassword, String userName,
			int userPhone, String userEmail, String[] role) {
		//檢查有沒有此user
		User existUser = userRepository.findByUserAccount(userAccount);
		if(existUser != null) {
			return "Please change the account";
		}
		User user = new User();
		user.setUserAccount(userAccount);
		user.setUserPassword(userPassword);
		user.setUserName(userName);
		user.setUserPhone(userPhone);
		user.setUserEmail(userEmail);
		//有沒有輸入到相同的role
		if(HaveSameRole(role)) {
			return "Have the same role";
		};
		//查看role table中，有沒有這個role，如果有，就加入到陣列裡
		if(authorNotInTable(role, user)) {
			return "Not Found This Role";
		};
		userRepository.save(user);
		return "Success";
	}
	
	//更新資料(對userRepository操作) 先找有沒有此ID，再把新的資料set進舊的資料
	public String updateUser(int Id, String userAccount, String userPassword, String userName,
			int userPhone, String userEmail, String[] role) {
		User user = userRepository.findById(Id)
				.orElseThrow(() -> new RuntimeException("Not Found"));
		//有沒有輸入到相同的role
		if(HaveSameRole(role)) {
			return "Have the same role";
		};
		//清除user跟role的關聯
		clearRelation(user);
	    //查看role table中，有沒有這個role，如果有，就加入到陣列裡
		if(authorNotInTable(role, user)) {
			return "Not Found This Role";
		};
		user.setUserAccount(userAccount);
		user.setUserPassword(userPassword);
		user.setUserName(userName);
		user.setUserPhone(userPhone);
		user.setUserEmail(userEmail);
		userRepository.save(user);
		return "Success";
	}
	
	//刪除資料(對userRepository操作) 先找有沒有此ID，然後再做刪除
	public String deleteUser(int Id) {
		User user = userRepository.findById(Id).orElse(null);
		if(user==null) {
			return "Not Found";
		}
		//清除user跟role的關聯
		clearRelation(user);
		userRepository.deleteById(Id);
		return "Success";
	}
	
	//登入帳號密碼
	@Transactional
	public String login(String userAccount, String userPassword) {
		boolean loginSuccessful = authenticateUser(userAccount, userPassword);
        if (loginSuccessful) {
            String token = jwtTokenGenerator.generateJwtToken(userAccount);
            return token;
//        	return "Success";
        } 
        return "account or password is wrong";
    }
	
	//驗證有無此使用者，以及對應輸入的密碼是否與userRepository中的密碼相同
	private boolean authenticateUser(String userAccount, String userPassword) {
        User user = userRepository.findByUserAccount(userAccount);
        if (user != null && user.getUserPassword().equals(userPassword)) {
            return true;
        }
        return false;
    }
	
	//註冊新的user(先確認輸入的兩個密碼是否相同，再確認userRepository中有沒有相同帳號名稱的user，都通過後，新增user物件，然後把輸入得資料set進去)
	public String register(String Account, String Password, String comfirmPassword,
			String Name, int Phone, String Email, String[] role) {
		User oldUser = userRepository.findByUserAccount(Account);
		if(!Password.equals(comfirmPassword)) {
			return "Password not match";
		}
		//不能有相同的帳號名稱
		else if(oldUser != null) {
			return "Please change account";
		}
		//密碼為4~16位數
		else if(Password.length()<4 || Password.length()>16) {
			return "Password is 4 to 16 digits";
		}
		//電話號為9-00-000-000~9-99-999-999區間
		else if(Phone < 900000000 || Phone > 999999999) {
			return "Phone form is wrong";
		}
		User user = new User();
		user.setUserAccount(Account);
		user.setUserPassword(Password);
		user.setUserName(Name);
		user.setUserPhone(Phone);
		user.setUserEmail(Email);
		//有沒有輸入到相同的role
		if(HaveSameRole(role)) {
			return "Have the same role";
		};
		//查看role table中，有沒有這個role，如果有，就加入到陣列裡
		if(authorNotInTable(role, user)) {
			return "Not Found This Role";
		};
		userRepository.save(user);
		return "Success";
	}
	
	//忘記密碼(確認有沒有此mail的user，有就確認新密碼跟確認密碼是否相同)
	public String forget(String userEmail, String Password, String comfirmPassword) {
		User user = userRepository.findByUserEmail(userEmail);
		if(user == null) {
			return "Not found this mail";
		}
		if(!Password.equals(comfirmPassword)) {
			return "Password not match";
		}
		user.setUserPassword(Password);
		userRepository.save(user);
		return "Success";
	}
	
	//登出(把redis中的token刪除)
	public String logout(String userAccount) {
        User user = userRepository.findByUserAccount(userAccount);
        if(!jwtTokenGenerator.keyExists(userAccount)) {
        	return "user did not login";
        }
		if(user!=null && user.getUserAccount().equals(userAccount)) {
			jwtTokenGenerator.deleteToken(userAccount);
			return "Success";
		}
		return "not found this account";
	}

	//是不是admin
	public boolean hasAdminRole(String userAccount) {
	    User user = userRepository.findByUserAccount(userAccount);
	    Boolean adminRole = false;
	    for(Role roles : user.getRoles()) {
	    	if(roles.getRole().equals("admin")) {
	    		adminRole = true;
	    	}
	    }
	    return adminRole;
	}
	
	//是不是user
	public boolean hasUserRole(String userAccount) {
	    User user = userRepository.findByUserAccount(userAccount);
	    Boolean adminRole = false;
	    for(Role roles : user.getRoles()) {
	    	if(roles.getRole().equals("user")) {
	    		adminRole = true;
	    	}
	    }
	    return adminRole;
	}
	
	//有沒有輸入到相同的角色
	private Boolean HaveSameRole(String[] role) {
		for(int i=0;i<role.length-1;i++) {
			for(int j=1;j<role.length;j++) {
				if(role[i].equals(role[j])){
					return true;
				}
			}
		}
		return false;
	}
	//清空所有跟這個user有關的role
	private void clearRelation(User user) {
		List<Role> roles = user.getRoles();
		for (Role role : roles) {
			role.getUsers().remove(user);
	    }
	    //清空這個user的role列表
		roles.clear();
	}
	//查看角色table中，有沒有這個role
	private Boolean authorNotInTable(String[] role, User user) {
		for(int i=0;i<role.length;i++) {
			Role existRole = roleRepository.findByRole(role[i]);
			if(existRole==null) {
				return true;
			}
			user.getRoles().add(existRole);
		}
		return false;
	}
}
