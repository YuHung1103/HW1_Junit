package com.example.demo.Integration;

import com.example.demo.Dao.AuthorRepository;
import com.example.demo.Dao.BookRepository;
import com.example.demo.Dao.UserRepository;
import com.example.demo.Dto.*;
import com.example.demo.Entity.Author;
import com.example.demo.Entity.Book;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    //用於java與JSON的數據轉換
    @Autowired
    private ObjectMapper objectMapper;

    //把有MockBean註解的物件都虛擬化入等等會進入的class
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;

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

        User user = new User();
        user.setUserId(1);
        user.setUserAccount("user");
        user.setUserPassword("user");
        user.setUserName("user");
        user.setUserPhone(987654321);
        user.setUserEmail("user@mail.com");
        user.setRoles(mockroles);

        List<Author> mockauthors = new ArrayList<>();
        Author author = new Author();
        author.setAuthorId(1);
        author.setAuthorName("author");
        author.setBirthday(LocalDate.of(1999, 9, 9));
        mockauthors.add(author);

        List<Book> mockbooks = new ArrayList<>();
        Book book = new Book();
        book.setBookId(1);
        book.setBookName("book");
        book.setSummary("summary");
        book.setPricing(500);
        book.setSellingPrice(450);
        book.setAuthors(mockauthors);

        //遇到...就回傳...
        when(userRepository.findByUserAccount(eq("user"))).thenReturn(user);
        when(bookRepository.findById(eq(1))).thenReturn(Optional.of(book));
        when(authorRepository.findByAuthorName(eq("author"))).thenReturn(author);
        when(bookRepository.findAll()).thenReturn(mockbooks);
//        when(roleRepository.findByRole(eq("admin"))).thenReturn(mockRole1);
//        when(roleRepository.findByRole(eq("user"))).thenReturn(mockRole2);
//        when(userRepository.findById(eq(1))).thenReturn(Optional.of(user));

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
    public void testGetAllBooks() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void testGetBook() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/book/{Id}", 1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddBook() throws Exception{
        String[] authors = {"author"};

        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("addbook");
        addBookRequest.setSummary("addsummary");
        addBookRequest.setPricing(300);
        addBookRequest.setSellingPrice(200);
        addBookRequest.setAuthorName(authors);

        String addBookRequestJson = objectMapper.writeValueAsString(addBookRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addBookRequestJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }

    @Test
    public void testUpdateBook() throws Exception{
        String[] authors = {"author"};

        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setBookName("updatebook");
        updateBookRequest.setSummary("updatesummary");
        updateBookRequest.setPricing(500);
        updateBookRequest.setSellingPrice(400);
        updateBookRequest.setAuthorName(authors);

        String updateBookRequestJson = objectMapper.writeValueAsString(updateBookRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/book/{Id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBookRequestJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }

    @Test
    public void testDeleteUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/{Id}", 1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }
}
