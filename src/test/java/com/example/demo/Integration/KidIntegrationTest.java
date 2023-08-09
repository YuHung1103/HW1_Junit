package com.example.demo.Integration;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.example.demo.Dao.FatherRepository;
import com.example.demo.Dao.KidRepository;
import com.example.demo.Dto.AddKidRequest;
import com.example.demo.Dto.UpdateKidRequest;
import com.example.demo.Entity.Father;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.Entity.Kid;

@SpringBootTest
@AutoConfigureMockMvc
public class KidIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    //用於java與JSON的數據轉換
    @Autowired
    private ObjectMapper objectMapper;

    //把有MockBean註解的物件都虛擬化入等等會進入的class
    @MockBean
    private FatherRepository fatherRepository;
    @MockBean
    private KidRepository kidRepository;

    @Test
    public void testCreateKid() throws Exception {
        //創建虛擬資料
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        //遇到...就回傳...
        when(fatherRepository.findByFatherName(eq("ftest"))).thenReturn(mockFather);

        AddKidRequest addKidRequest = new AddKidRequest();
        addKidRequest.setKidName("test");
        addKidRequest.setFatherName("ftest");

        //把資料轉換成JSON格式
        String requestJson = objectMapper.writeValueAsString(addKidRequest);

        //傳送資料給controller，並做了斷言
        mockMvc.perform(MockMvcRequestBuilders.post("/kid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        //驗證方法有沒有被使用
        verify(fatherRepository).findByFatherName(eq("ftest"));
    }

    @Test
    public void testGetAllKids() throws Exception {
        //創建虛擬資料
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        List<Kid> mockKids = new ArrayList<>();

        Kid kid1 = new Kid();
        kid1.setKidName("kid1");
        kid1.setFather(mockFather);
        mockKids.add(kid1);

        Kid kid2 = new Kid();
        kid2.setKidName("kid2");
        kid2.setFather(mockFather);
        mockKids.add(kid2);

        //遇到...就回傳...
        when(kidRepository.findAll()).thenReturn(mockKids);

        //傳送資料給controller，並做了斷言
        mockMvc.perform(MockMvcRequestBuilders.get("/kid"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        //驗證方法有沒有被使用
        verify(kidRepository).findAll();

        //可察看最終回傳值
//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/kid"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn().getResponse();
//
//        String responseBody = response.getContentAsString();
//        System.out.println("Response body: " + responseBody); // 输出接口的实际响应内容
    }

    @Test
    public void testGetKid() throws Exception {
        //創建虛擬資料
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        Kid kid1 = new Kid();
        kid1.setKidId(1);
        kid1.setKidName("kid1");
        kid1.setFather(mockFather);

        //遇到...就回傳...
        when(kidRepository.findById(eq(1))).thenReturn(Optional.of(kid1));

        //傳送資料給controller，並做了斷言
        mockMvc.perform(MockMvcRequestBuilders.get("/kid/{Id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //驗證方法有沒有被使用
        verify(kidRepository).findById(eq(1));
    }

    @Test
    public void testUpdateKid() throws Exception {
        //創建虛擬資料
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        Kid mockKid = new Kid();
        mockKid.setKidId(1);
        mockKid.setKidName("KidName");
        mockKid.setFather(mockFather);

        Father newMockFather = new Father();
        newMockFather.setFatherId(2);
        newMockFather.setFatherName("FatherName");
        newMockFather.setKids(null);

        //遇到...救回傳...
        when(kidRepository.findById(eq(1))).thenReturn(Optional.of(mockKid));
        when(fatherRepository.findByFatherName(eq("FatherName"))).thenReturn(newMockFather);

        //建立一個傳給controller需要的物件
        UpdateKidRequest updateKidRequest = new UpdateKidRequest();
        updateKidRequest.setKidName("test");
        updateKidRequest.setFatherName("FatherName");

        //把資料轉換成JSON格式
        String requestJson = objectMapper.writeValueAsString(updateKidRequest);

        //傳送資料給controller，並做了斷言
        mockMvc.perform(MockMvcRequestBuilders.put("/kid/{Id}", 1)
                        .content(requestJson)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        //驗證方法有沒有被使用
        verify(kidRepository).findById(eq(1));
        verify(fatherRepository).findByFatherName(eq("FatherName"));
        verify(kidRepository).save(mockKid);
    }

    @Test
    public void testDeleteKid() throws Exception {
        //創建虛擬資料
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        Kid mockKid = new Kid();
        mockKid.setKidId(1);
        mockKid.setKidName("KidName");
        mockKid.setFather(mockFather);

        //遇到...就回傳...
        when(kidRepository.findById(eq(1))).thenReturn(Optional.of(mockKid));

        //傳送資料給controller，並做了斷言
        mockMvc.perform(MockMvcRequestBuilders.delete("/kid/{Id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        //驗證方法有沒有被使用
        verify(kidRepository).findById(eq(1));
        verify(kidRepository).deleteById(eq(1));
    }
}
