package com.example.demo.Integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.Controller.KidController;
import com.example.demo.Dao.FatherRepository;
import com.example.demo.Dao.KidRepository;
import com.example.demo.Dto.AddKidRequest;
import com.example.demo.Entity.Father;
import com.example.demo.Implement.KidServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import com.example.demo.Entity.Kid;
import com.example.demo.Service.KidService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KidIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private KidServiceImpl kidServiceImpl;

    @Mock
    private FatherRepository fatherRepository;

//    @BeforeEach
//    public void setUp() {
//        Assertions.assertThat(kidService).isNotNull();
//    }

    @Test
    public void testCreateKid() throws Exception {
        // 模拟一个虚拟的 father 数据
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        when(fatherRepository.findByFatherName(anyString())).thenReturn(mockFather);
        Father fa = fatherRepository.findByFatherName("ftest");

        AddKidRequest addKidRequest = new AddKidRequest();
        addKidRequest.setKidName("test");
        addKidRequest.setFatherName("ftest");

        String requestJson = objectMapper.writeValueAsString(addKidRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/kid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }


//    @Test
//    public void testCreateKid() throws Exception {
//        // Mock the behavior of fatherRepository.findByFatherName to return a mocked Father object
//        when(fatherRepository.findByFatherName("ftest")).thenReturn(mock(Father.class));
////        Father mockFather = new Father();
////        mockFather.setFatherName("ftest");
////        when(fatherRepository.findByFatherName("ftest")).thenReturn(mockFather);
////        Father a = fatherRepository.findByFatherName("ftest");
//
//        AddKidRequest addKidRequest = new AddKidRequest();
//        addKidRequest.setKidName("test");
//        addKidRequest.setFatherName("ftest");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestJson = objectMapper.writeValueAsString(addKidRequest);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/kid")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Success"));
//
////        // Verify that the methods were called with the correct arguments
////        verify(fatherRepository, times(1)).findByFatherName("ftest");
////        verify(kidService, times(1)).createKid("test", "ftest");
//    }

//    @Test
//    public void testCreateKid() throws Exception {
//        String kidName = "John";
//        String fatherName = "Doe";
//        String responseMessage = "Success"; // 假设成功响应消息
//
//        when(kidService.createKid(eq(kidName), eq(fatherName))).thenReturn("Success");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/kid")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"kidName\": \"" + kidName + "\", \"fatherName\": \"" + fatherName + "\"}"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//                //.andExpect(MockMvcResultMatchers.content().string("Success"));
//        verify(kidService).createKid(kidName,fatherName);
//    }


//    @Test
//    public void testGetAllKids() throws Exception {
//        // 添加测试数据到数据库或模拟数据
//        // ...
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/kid"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    public void testUpdateKid() throws Exception {
//        // 模拟请求数据
//        String requestBody = "{\"kidName\":\"UpdatedKid\",\"fatherName\":\"UpdatedFather\"}";
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/kid/{Id}", 1)
//                        .content(requestBody)
//                        .contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Success"));
//
//        // 验证更新后的数据是否正确
//        // ...
//    }
//
//    @Test
//    public void testDeleteKid() throws Exception {
//        // 模拟删除操作
//        mockMvc.perform(MockMvcRequestBuilders.delete("/kid/{Id}", 1))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Success"));
//
//        // 验证数据是否被成功删除
//        // ...
//    }
//
//    // 编写其他测试用例...
}

