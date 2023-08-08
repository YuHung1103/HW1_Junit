package com.example.demo.Implement;

import com.example.demo.Dao.FatherRepository;
import com.example.demo.Dao.KidRepository;
import com.example.demo.Entity.Author;
import com.example.demo.Entity.Father;
import com.example.demo.Entity.Kid;
import com.example.demo.Service.KidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KidServiceImpl implements KidService {

    @Autowired
    private KidRepository kidRepository;
    @Autowired
    private FatherRepository fatherRepository;

    public List<Kid> getAllKids(){
        return kidRepository.findAll();
    }

    public Kid getKid(int Id) {
        return kidRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
    }

    public String createKid(String kidName, String fatherName) {
        Kid kid = new Kid();
        kid.setKidName(kidName);

        //查看父親table中，有沒有這位父親
        Father existFather = fatherRepository.findByFatherName(fatherName);
        if(existFather==null) {
            return "NOT FOUND THIS FATHER";
        }
        kid.setFather(existFather);
        kidRepository.save(kid);
        return "Success";
    }

    public String updateKid(int Id, String kidName, String fatherName) {
        Kid kid = kidRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not foound"));
        //查看父親table中，有沒有這位父親
        Father existFather = fatherRepository.findByFatherName(fatherName);
        if(existFather==null) {
            return "NOT FOUND THIS FATHER";
        }
        kid.setKidName(kidName);
        kid.setFather(existFather);
        kidRepository.save(kid);
        return "Success";
    }

    public String deleteKid(int Id) {
        Kid kid = kidRepository.findById(Id).orElse(null);
        if(kid==null) {
            return "Not Found";
        }
        kidRepository.deleteById(Id);
        return "Success";
    }

    //清空所有跟本書有關的作者
//    private void clearRelation(Book book) {
//        List<Author> authors = book.getAuthors();
//        for (Author author : authors) {
//            author.getBooks().remove(book);
//        }
//        //清空這本書的作者列表
//        authors.clear();
//    }
}
