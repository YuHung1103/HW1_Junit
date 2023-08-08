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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @MockBean
    private FatherRepository fatherRepository;

    @MockBean
    private KidRepository kidRepository;

    @Test
    public void testCreateKid() throws Exception {
        // 模拟一个虚拟的 father 数据
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        when(fatherRepository.findByFatherName(eq("ftest"))).thenReturn(mockFather);

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
//    public void testGetAllKids() throws Exception {
//        // 模拟一个虚拟的 father 数据
//        Father mockFather = new Father();
//        mockFather.setFatherId(1);
//        mockFather.setFatherName("ftest");
//        mockFather.setKids(null);
//        // 创建模拟的 Kid 对象列表
//        List<Kid> mockKids = new ArrayList<>();
//        //第一筆小孩資料
//        Kid kid1 = new Kid();
//        kid1.setKidName("kid1");
//        kid1.setFather(mockFather);
//        mockKids.add(kid1);
//        //第二筆小孩資料
//        Kid kid2 = new Kid();
//        kid2.setKidName("kid2");
//        kid2.setFather(mockFather);
//        mockKids.add(kid2);
//
//        // 模拟 kidRepository.findAll 方法返回模拟的 Kid 对象列表
//        when(kidRepository.findAll()).thenReturn(mockKids);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/kid"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    public void testUpdateKid() throws Exception {
//        // 模拟一个虚拟的 father 数据
//        Father mockFather = new Father();
//        mockFather.setFatherId(1);
//        mockFather.setFatherName("ftest");
//        mockFather.setKids(null);
//
//        // 创建模拟的 Kid 对象和 Father 对象
//        Kid mockKid = new Kid();
//        mockKid.setKidId(1);
//        mockKid.setKidName("KidName");
//        mockKid.setFather(mockFather);
//
//        Father newMockFather = new Father();
//        mockFather.setFatherName("FatherName");
//
//        // 模拟 kidRepository.findById 方法返回模拟的 Kid 对象
//        when(kidRepository.findById(eq(1))).thenReturn(Optional.of(mockKid));
//
//        // 模拟 fatherRepository.findByFatherName 方法返回模拟的 Father 对象
//        when(fatherRepository.findByFatherName(eq("FatherName"))).thenReturn(newMockFather);
//
//        // 调用实际的 updateKid 方法
//        String result = kidServiceImpl.updateKid(1, "NewKidName", "FatherName");
//
//        // 验证返回结果是否与预期相符
//        assertEquals("Success", result);
//
//        // 验证 kidRepository.findById 方法是否被调用
//        verify(kidRepository).findById(eq(1));
//
//        // 验证 fatherRepository.findByFatherName 方法是否被调用
//        verify(fatherRepository).findByFatherName(eq("FatherName"));
//
//        // 验证 kidRepository.save 方法是否被调用
//        verify(kidRepository).save(mockKid);
//
////        // 模拟请求数据
////        String requestBody = "{\"kidName\":\"UpdatedKid\",\"fatherName\":\"UpdatedFather\"}";
////
////        mockMvc.perform(MockMvcRequestBuilders.put("/kid/{Id}", 1)
////                        .content(requestBody)
////                        .contentType("application/json"))
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.content().string("Success"));
//
//    }
//
//    @Test
//    public void testDeleteKid() throws Exception {
//        // 模拟一个虚拟的 father 数据
//        Father mockFather = new Father();
//        mockFather.setFatherId(1);
//        mockFather.setFatherName("ftest");
//        mockFather.setKids(null);
//
//        Kid mockKid = new Kid();
//        mockKid.setKidId(1);
//        mockKid.setKidName("KidName");
//        mockKid.setFather(mockFather);
//
//
//        // 模拟 kidRepository.findById 方法返回模拟的 Kid 对象
//        when(kidRepository.findById(eq(1))).thenReturn(Optional.of(mockKid));
//
//        // 调用实际的 deleteKid 方法
//        String result = kidServiceImpl.deleteKid(1);
//
//        // 验证返回结果是否与预期相符
//        assertEquals("Success", result);
//
//        // 验证 kidRepository.findById 方法是否被调用
//        verify(kidRepository).findById(eq(1));
//
//        // 验证 kidRepository.deleteById 方法是否被调用
//        verify(kidRepository).deleteById(eq(1));
//
////        // 模拟删除操作
////        mockMvc.perform(MockMvcRequestBuilders.delete("/kid/{Id}", 1))
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.content().string("Success"));
//
//    }

}

