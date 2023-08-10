package com.example.demo.Integration;

import com.example.demo.Dao.RoleRepository;
import com.example.demo.Dao.UserRepository;
import com.example.demo.Dto.*;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegationTest {

    @Autowired
    private MockMvc mockMvc;

    //用於java與JSON的數據轉換
    @Autowired
    private ObjectMapper objectMapper;

    //把有MockBean註解的物件都虛擬化入等等會進入的class
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;

    private String token;

    @BeforeEach
    public void testLogin() throws Exception{
        //創建虛擬資料
        List<Role> mockroles = new ArrayList<>();
        Role mockRole1 = new Role();
        mockRole1.setRoleId(1);
        mockRole1.setRole("admin");
        mockroles.add(mockRole1);
        Role mockRole2 = new Role();
        mockRole2.setRoleId(2);
        mockRole2.setRole("user");
        mockroles.add(mockRole2);

        List<User> mockusers = new ArrayList<>();
        User user = new User();
        user.setUserId(1);
        user.setUserAccount("user");
        user.setUserPassword("user");
        user.setUserName("user");
        user.setUserPhone(987654321);
        user.setUserEmail("user@mail.com");
        user.setRoles(mockroles);
        mockusers.add(user);

        //遇到...就回傳...
        when(userRepository.findByUserAccount(eq("user"))).thenReturn(user);
        when(roleRepository.findByRole(eq("admin"))).thenReturn(mockRole1);
        when(roleRepository.findByRole(eq("user"))).thenReturn(mockRole2);
        when(userRepository.findByUserEmail(eq("user@mail.com"))).thenReturn(user);
        when(userRepository.findById(eq(1))).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(mockusers);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserAccount("user");
        loginRequest.setUserPassword("user");

        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);

        //可察看最終回傳值
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        token = response.getContentAsString();
//        System.out.println(token);
    }

    @Test
    public void testRegister() throws Exception {
        String[] roles = {"admin", "user"};
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserAccount("user2");
        registerRequest.setUserPassword("user2");
        registerRequest.setConfirmPassword("user2");
        registerRequest.setUserName("user2");
        registerRequest.setUserPhone(963852741);
        registerRequest.setUserEmail("user2@mail.com");
        registerRequest.setRole(roles);

        String registerRequestJson = objectMapper.writeValueAsString(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        //可察看最終回傳值
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        String result = response.getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testForget() throws Exception{
        ForgetRequest forgetRequest = new ForgetRequest();
        forgetRequest.setUserEmail("user@mail.com");
        forgetRequest.setUserPassword("test");
        forgetRequest.setConfirmPassword("test");

        String forgetRequestJson = objectMapper.writeValueAsString(forgetRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/forget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(forgetRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        //可察看最終回傳值
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/forget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(forgetRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        String result = response.getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testLogout() throws Exception{
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setAccount("user");

        String logoutRequestJson = objectMapper.writeValueAsString(logoutRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/logout")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(logoutRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }

    @Test
    public void testGetAllUsers() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //可察看最終回傳值
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        String result = response.getContentAsString();
        System.out.println(result);
    }
    @Test
    public void testGetUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user/{Id}", 1)
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //可察看最終回傳值
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/user/{Id}", 1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        String result = response.getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testAddUser() throws Exception{
        String[] roles = {"user"};

        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setUserAccount("adduser");
        addUserRequest.setUserPassword("adduser");
        addUserRequest.setUserName("adduser");
        addUserRequest.setUserPhone(987654321);
        addUserRequest.setUserEmail("adduser@mail.com");
        addUserRequest.setRole(roles);

        String addUserRequestJson = objectMapper.writeValueAsString(addUserRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addUserRequestJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        //可察看最終回傳值
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addUserRequestJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        String result = response.getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testUpdateUser() throws Exception{
        String[] roles = {"user"};

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserAccount("updateuser");
        updateUserRequest.setUserPassword("updateuser");
        updateUserRequest.setUserName("updateuser");
        updateUserRequest.setUserPhone(987654321);
        updateUserRequest.setUserEmail("updateuser@mail.com");
        updateUserRequest.setRole(roles);

        String updateUserRequestJson = objectMapper.writeValueAsString(updateUserRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/{Id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserRequestJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        //可察看最終回傳值
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/user/{Id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserRequestJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        String result = response.getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testDeleteUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{Id}", 1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }
}
