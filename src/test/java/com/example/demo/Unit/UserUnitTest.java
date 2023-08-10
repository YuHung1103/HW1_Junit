package com.example.demo.Unit;

import com.example.demo.Dao.RoleRepository;
import com.example.demo.Dao.UserRepository;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Implement.UserServiceImpl;
import com.example.demo.Jwt.JwtTokenGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserUnitTest {

    //會把下列三個有使用Mock註解的物件，注入進這個class中
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    //創建虛擬物件
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private JwtTokenGenerator jwtTokenGenerator;

    @Test
    public void testCreateUser(){
        //創建虛擬資料
        Role mockRole1 = new Role();
        mockRole1.setRoleId(1);
        mockRole1.setRole("admin");

        Role mockRole2 = new Role();
        mockRole2.setRoleId(2);
        mockRole2.setRole("user");

        //遇到...就回傳...
        when(roleRepository.findByRole(eq("admin"))).thenReturn(mockRole1);
        when(roleRepository.findByRole(eq("user"))).thenReturn(mockRole2);

        //使用userServiceImpl的實際方法，並存入回傳值
        String[] roles = {"admin","user"};
        String result = userServiceImpl.createUser("test","test","test",987654321,"test@mail.com",roles);

        //斷言
        assertEquals("Success", result);
    }

    @Test
    public void testGetAllUsers(){
        //創建虛擬資料
        List<Role> mockroles= new ArrayList<>();

        Role mockRole = new Role();
        mockRole.setRoleId(1);
        mockRole.setRole("admin");
        mockroles.add(mockRole);

        List<User> mockUsers = new ArrayList<>();

        User user1 = new User();
        user1.setUserAccount("user1");
        user1.setUserPassword("user1");
        user1.setUserName("user1");
        user1.setUserPhone(987654321);
        user1.setUserEmail("user1@mail.com");
        user1.setRoles(mockroles);
        mockUsers.add(user1);

        User user2 = new User();
        user2.setUserAccount("user2");
        user2.setUserPassword("user2");
        user2.setUserName("user2");
        user2.setUserPhone(987654321);
        user2.setUserEmail("user2@mail.com");
        user2.setRoles(mockroles);
        mockUsers.add(user2);

        //遇到...就回傳...
        when(userRepository.findAll()).thenReturn(mockUsers);

        //使用userServiceImpl的實際方法，並存入回傳值
        List<User> result = userServiceImpl.getAllUsers();

        //斷言
        assertEquals(mockUsers.size(), result.size());
        assertEquals(mockUsers.get(0).getUserName(), result.get(0).getUserName());
        assertEquals(mockUsers.get(1).getUserName(), result.get(1).getUserName());

        //驗證方法有沒有被使用
        verify(userRepository).findAll();
    }

    @Test
    public void testGetBook(){
        //創建虛擬資料
        List<Role> mockroles= new ArrayList<>();

        Role mockRole = new Role();
        mockRole.setRoleId(1);
        mockRole.setRole("admin");
        mockroles.add(mockRole);

        User user = new User();
        user.setUserId(1);
        user.setUserAccount("user");
        user.setUserPassword("user");
        user.setUserName("user");
        user.setUserPhone(987654321);
        user.setUserEmail("user@mail.com");
        user.setRoles(mockroles);

        //遇到...就回傳...
        when(userRepository.findById(eq(1))).thenReturn(Optional.of(user));

        //使用userServiceImpl的實際方法，並存入回傳值
        User result = userServiceImpl.getUser(1);

        //斷言
        assertEquals(user.getUserName(), result.getUserName());

        //驗證方法有沒有被使用
        verify(userRepository).findById(eq(1));
    }

    @Test
    public void testUpdateBook(){
        //創建虛擬資料
        List<Role> mockroles= new ArrayList<>();

        Role mockRole = new Role();
        mockRole.setRoleId(1);
        mockRole.setRole("admin");
        mockroles.add(mockRole);

        User user = new User();
        user.setUserId(1);
        user.setUserAccount("user");
        user.setUserPassword("user");
        user.setUserName("user");
        user.setUserPhone(987654321);
        user.setUserEmail("user@mail.com");
        user.setRoles(mockroles);

        //遇到...就回傳...
        when(userRepository.findById(eq(1))).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(eq("admin"))).thenReturn(mockRole);

        //使用userServiceImpl的實際方法，並存入回傳值
        String[] roles = {"admin"};
        String result = userServiceImpl.updateUser(1,"test", "test", "test", 963852741, "test@mail.com", roles);

        //斷言
        assertEquals("Success", result);

        //驗證方法有沒有被使用
        verify(userRepository).findById(eq(1));
        verify(roleRepository).findByRole(eq("admin"));
        verify(userRepository).save(user);
    }

    @Test
    public void testDeleteBook(){
        //創建虛擬資料
        List<Role> mockroles= new ArrayList<>();

        Role mockRole = new Role();
        mockRole.setRoleId(1);
        mockRole.setRole("admin");
        mockroles.add(mockRole);

        User user = new User();
        user.setUserId(1);
        user.setUserAccount("user");
        user.setUserPassword("user");
        user.setUserName("user");
        user.setUserPhone(987654321);
        user.setUserEmail("user@mail.com");
        user.setRoles(mockroles);

        //遇到...就回傳...
        when(userRepository.findById(eq(1))).thenReturn(Optional.of(user));

        //使用userServiceImpl的實際方法，並存入回傳值
        String result = userServiceImpl.deleteUser(1);

        //斷言
        assertEquals("Success", result);

        //驗證方法有沒有被使用
        verify(userRepository).findById(eq(1));
        verify(userRepository).deleteById(eq(1));
    }

    @Test
    public void testLogin(){
        //創建虛擬資料
        User user = new User();
        user.setUserId(1);
        user.setUserAccount("user");
        user.setUserPassword("user");
        user.setUserName("user");
        user.setUserPhone(987654321);
        user.setUserEmail("user@mail.com");

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIn0.9GnZFIiRocAmNvFtRa1-vFb8wj1p1_zJzLhQ1Oc2vZE";

        //遇到...就回傳...
        when(userRepository.findByUserAccount(eq("user"))).thenReturn(user);
        when(jwtTokenGenerator.generateJwtToken(eq("user"))).thenReturn(token);

        //使用userServiceImpl的實際方法，並存入回傳值
        String result = userServiceImpl.login("user","user");

        //斷言
        assertEquals(token, result);

        //驗證方法有沒有被使用
        verify(userRepository).findByUserAccount(eq("user"));
        verify(jwtTokenGenerator).generateJwtToken(eq("user"));
    }

    @Test
    public void testRegister(){
        //創建虛擬資料
        Role mockRole1 = new Role();
        mockRole1.setRoleId(1);
        mockRole1.setRole("admin");

        Role mockRole2 = new Role();
        mockRole2.setRoleId(2);
        mockRole2.setRole("user");

        //遇到...就回傳...
        when(roleRepository.findByRole(eq("admin"))).thenReturn(mockRole1);
        when(roleRepository.findByRole(eq("user"))).thenReturn(mockRole2);

        //使用userServiceImpl的實際方法，並存入回傳值
        String[] roles = {"admin","user"};
        String result = userServiceImpl.register("test","test","test","test",987654321,"test@mail.com",roles);

        //斷言
        assertEquals("Success", result);

        //驗證方法有沒有被使用
        verify(roleRepository).findByRole(eq("admin"));
        verify(roleRepository).findByRole(eq("user"));
    }

    @Test
    public void testForget(){
        //創建虛擬資料
        User user = new User();
        user.setUserId(1);
        user.setUserAccount("user");
        user.setUserPassword("user");
        user.setUserName("user");
        user.setUserPhone(987654321);
        user.setUserEmail("user@mail.com");

        //遇到...就回傳...
        when(userRepository.findByUserEmail(eq("user@mail.com"))).thenReturn(user);

        //使用userServiceImpl的實際方法，並存入回傳值
        String result = userServiceImpl.forget("user@mail.com","test","test");

        //斷言
        assertEquals("Success", result);

        //驗證方法有沒有被使用
        verify(userRepository).findByUserEmail(eq("user@mail.com"));
    }

    @Test
    public void testLogout(){
        //創建虛擬資料
        User user = new User();
        user.setUserId(1);
        user.setUserAccount("user");
        user.setUserPassword("user");
        user.setUserName("user");
        user.setUserPhone(987654321);
        user.setUserEmail("user@mail.com");

        //遇到...就回傳...
        when(userRepository.findByUserAccount(eq("user"))).thenReturn(user);
        when(jwtTokenGenerator.keyExists(eq("user"))).thenReturn(true);

        //使用userServiceImpl的實際方法，並存入回傳值
        String result = userServiceImpl.logout("user");

        //斷言
        assertEquals("Success", result);

        //驗證方法有沒有被使用
        verify(userRepository).findByUserAccount(eq("user"));
        verify(jwtTokenGenerator).keyExists(eq("user"));
    }
}
