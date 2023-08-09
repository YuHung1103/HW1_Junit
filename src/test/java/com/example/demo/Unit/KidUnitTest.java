package com.example.demo.Unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.example.demo.Dao.FatherRepository;
import com.example.demo.Dao.KidRepository;
import com.example.demo.Entity.Father;
import com.example.demo.Implement.KidServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.Entity.Kid;

@SpringBootTest
@AutoConfigureMockMvc
public class KidUnitTest {

    //會把下列兩個有使用Mock註解的物件，注入進這個class中
    @InjectMocks
    private KidServiceImpl kidServiceImpl;

    //創建虛擬物件
    @Mock
    private FatherRepository fatherRepository;
    @Mock
    private KidRepository kidRepository;

    @Test
    public void testCreateKid(){
        //創建虛擬資料
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        //遇到...就回傳...
        when(fatherRepository.findByFatherName(eq("ftest"))).thenReturn(mockFather);

        //使用kidServiceImpl的實際方法，並存入回傳值
        String result = kidServiceImpl.createKid("test", "ftest");

        //斷言
        assertEquals("Success", result);
    }

    @Test
    public void testGetAllKids(){
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

        //使用kidServiceImpl的實際方法，並存入回傳值
        List<Kid> result = kidServiceImpl.getAllKids();

        //斷言
        assertEquals(mockKids.size(), result.size());
        assertEquals(mockKids.get(0).getKidName(), result.get(0).getKidName());
        assertEquals(mockKids.get(1).getKidName(), result.get(1).getKidName());

        //驗證方法有沒有被使用
        verify(kidRepository).findAll();
    }

    @Test
    public void testGetKid(){
        //創建虛擬資料
        Father mockFather = new Father();
        mockFather.setFatherId(1);
        mockFather.setFatherName("ftest");
        mockFather.setKids(null);

        Kid kid1 = new Kid();
        kid1.setKidName("kid1");
        kid1.setFather(mockFather);

        //遇到...就回傳...
        when(kidRepository.findById(eq(1))).thenReturn(Optional.of(kid1));

        //使用kidServiceImpl的實際方法，並存入回傳值
        Kid result = kidServiceImpl.getKid(1);

        //斷言
        assertEquals(kid1.getKidName(), result.getKidName());

        //驗證方法有沒有被使用
        verify(kidRepository).findById(eq(1));
    }

    @Test
    public void testUpdateKid(){
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

        //遇到...就回傳...
        when(kidRepository.findById(eq(1))).thenReturn(Optional.of(mockKid));
        when(fatherRepository.findByFatherName(eq("FatherName"))).thenReturn(newMockFather);

        //使用kidServiceImpl的實際方法，並存入回傳值
        String result = kidServiceImpl.updateKid(1, "NewKidName", "FatherName");

        //斷言
        assertEquals("Success", result);

        //驗證方法有沒有被使用
        verify(kidRepository).findById(eq(1));
        verify(fatherRepository).findByFatherName(eq("FatherName"));
        verify(kidRepository).save(mockKid);
    }

    @Test
    public void testDeleteKid(){
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

        //使用kidServiceImpl的實際方法，並存入回傳值
        String result = kidServiceImpl.deleteKid(1);

        //斷言
        assertEquals("Success", result);

        //驗證方法有沒有被使用
        verify(kidRepository).findById(eq(1));
        verify(kidRepository).deleteById(eq(1));
    }
}

