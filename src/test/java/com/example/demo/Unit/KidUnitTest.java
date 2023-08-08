package com.example.demo.Unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.example.demo.Dao.FatherRepository;
import com.example.demo.Dao.KidRepository;
import com.example.demo.Entity.Father;
import com.example.demo.Implement.KidServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.Entity.Kid;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KidUnitTest {

    @InjectMocks
    private KidServiceImpl kidServiceImpl;

    @Mock
    private FatherRepository fatherRepository;

    @Mock
    private KidRepository kidRepository;

    @Test
    public void testCreateKid(){
        // 模拟一个虚拟的 father 数据
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        when(fatherRepository.findByFatherName(eq("ftest"))).thenReturn(mockFather);

        String result = kidServiceImpl.createKid("test", "ftest");

        assertEquals("Success", result);
    }

    @Test
    public void testGetKid(){
        // 模拟一个虚拟的 father 数据
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        //第一筆小孩資料
        Kid kid1 = new Kid();
        kid1.setKidName("kid1");
        kid1.setFather(mockFather);

        when(kidRepository.findById(1)).thenReturn(Optional.of(kid1));

        Kid result = kidServiceImpl.getKid(1);

        assertEquals(kid1.getKidName(), result.getKidName());

        verify(kidRepository).findById(1);
    }

    @Test
    public void testGetAllKids(){
        // 模拟一个虚拟的 father 数据
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);
        // 创建模拟的 Kid 对象列表
        List<Kid> mockKids = new ArrayList<>();
        //第一筆小孩資料
        Kid kid1 = new Kid();
        kid1.setKidName("kid1");
        kid1.setFather(mockFather);
        mockKids.add(kid1);
        //第二筆小孩資料
        Kid kid2 = new Kid();
        kid2.setKidName("kid2");
        kid2.setFather(mockFather);
        mockKids.add(kid2);

        // 模拟 kidRepository.findAll 方法返回模拟的 Kid 对象列表
        when(kidRepository.findAll()).thenReturn(mockKids);

        // 调用实际的 getAllKids 方法
        List<Kid> result = kidServiceImpl.getAllKids();

        // 验证结果是否与预期相符
        assertEquals(mockKids.size(), result.size());
        assertEquals(mockKids.get(0).getKidName(), result.get(0).getKidName());
        assertEquals(mockKids.get(1).getKidName(), result.get(1).getKidName());

        // 验证 kidRepository.findAll 方法是否被调用
        verify(kidRepository).findAll();
    }

    @Test
    public void testUpdateKid(){
        // 模拟一个虚拟的 father 数据
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        // 创建模拟的 Kid 对象和 Father 对象
        Kid mockKid = new Kid();
        mockKid.setKidId(1);
        mockKid.setKidName("KidName");
        mockKid.setFather(mockFather);

        Father newMockFather = new Father();
        mockFather.setFatherName("FatherName");

        // 模拟 kidRepository.findById 方法返回模拟的 Kid 对象
        when(kidRepository.findById(eq(1))).thenReturn(Optional.of(mockKid));

        // 模拟 fatherRepository.findByFatherName 方法返回模拟的 Father 对象
        when(fatherRepository.findByFatherName(eq("FatherName"))).thenReturn(newMockFather);

        // 调用实际的 updateKid 方法
        String result = kidServiceImpl.updateKid(1, "NewKidName", "FatherName");

        // 验证返回结果是否与预期相符
        assertEquals("Success", result);

        // 验证 kidRepository.findById 方法是否被调用
        verify(kidRepository).findById(eq(1));

        // 验证 fatherRepository.findByFatherName 方法是否被调用
        verify(fatherRepository).findByFatherName(eq("FatherName"));

        // 验证 kidRepository.save 方法是否被调用
        verify(kidRepository).save(mockKid);
    }

    @Test
    public void testDeleteKid(){
        // 模拟一个虚拟的 father 数据
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        Kid mockKid = new Kid();
        mockKid.setKidId(1);
        mockKid.setKidName("KidName");
        mockKid.setFather(mockFather);

        // 模拟 kidRepository.findById 方法返回模拟的 Kid 对象
        when(kidRepository.findById(eq(1))).thenReturn(Optional.of(mockKid));

        // 调用实际的 deleteKid 方法
        String result = kidServiceImpl.deleteKid(1);

        // 验证返回结果是否与预期相符
        assertEquals("Success", result);

        // 验证 kidRepository.findById 方法是否被调用
        verify(kidRepository).findById(eq(1));

        // 验证 kidRepository.deleteById 方法是否被调用
        verify(kidRepository).deleteById(eq(1));
    }
}

