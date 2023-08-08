package com.example.demo.Implement;

import com.example.demo.Dao.FatherRepository;
import com.example.demo.Dao.KidRepository;
import com.example.demo.Entity.Author;
import com.example.demo.Entity.Book;
import com.example.demo.Entity.Father;
import com.example.demo.Entity.Kid;
import com.example.demo.Service.FatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FatherServiceImpl implements FatherService {

    @Autowired
    private FatherRepository fatherRepository;
    @Autowired
    private KidRepository kidRepository;

    public List<Father> getAllFathers(){
        return fatherRepository.findAll();
    }

    public Father getFather(int Id) {
        return fatherRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
    }

    public String createFather(String fatherName, String[] kidName) {
        Father father = new Father();
        father.setFatherName(fatherName);
        //有沒有輸入到相同的孩子名稱
        if(HaveSameName(kidName)) {
            return "Have the same kid name";
        };
        //查看kid table中，有沒有這位kid，如果有，就加入到陣列裡
        if(kidNotInTable(kidName, father)) {
            return "Not Found This Author";
        };
        fatherRepository.save(father);
        return "Success";
    }

    public String updateFather(int Id, String fatherName,  String[] kidName) {
        Father father = fatherRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
        //有沒有輸入到相同的孩子名稱
        if(HaveSameName(kidName)) {
            return "Have the same author name";
        };
        //清除作者跟書的關聯
//        clearRelation(father);
        //查看kid table中，有沒有這位kid，如果有，就加入到陣列裡
        if(kidNotInTable(kidName, father)) {
            return "Not Found This Author";
        };
        father.setFatherName(fatherName);
        System.out.println(father.getKids());
        fatherRepository.save(father);
        return "Success";
    }

    public String deleteFather(int Id) {
        Father father = fatherRepository.findById(Id).orElse(null);
        if(father==null) {
            return "Not Found";
        }
        //清除作者跟書的關聯
//        clearRelation(book);
        fatherRepository.deleteById(Id);
        return "Success";
    }

    //有沒有輸入到相同的小孩名稱
    private Boolean HaveSameName(String[] kidName) {
        for(int i=0;i<kidName.length-1;i++) {
            for(int j=1;j<kidName.length;j++) {
                if(kidName[i].equals(kidName[j])){
                    return true;
                }
            }
        }
        return false;
    }
    //清空所有跟本書有關的作者
    private void clearRelation(Book book) {
        List<Author> authors = book.getAuthors();
        for (Author author : authors) {
            author.getBooks().remove(book);
        }
        //清空這本書的作者列表
        authors.clear();
    }
    //查看kid table中，有沒有這位kid
    private Boolean kidNotInTable(String[] kidName, Father father) {
        for(int i=0;i<kidName.length;i++) {
            Kid existKid = kidRepository.findBykidName(kidName[i]);
            if(existKid==null) {
                return true;
            }
            father.getKids().add(existKid);
        }
        return false;
    }
}
