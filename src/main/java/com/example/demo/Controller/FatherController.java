package com.example.demo.Controller;

import com.example.demo.Dto.AddFatherRequest;
import com.example.demo.Dto.UpdateFatherRequest;
import com.example.demo.Entity.Father;
import com.example.demo.Service.FatherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "父親管理", description = "可控制父親資料")
@RestController
public class FatherController {
    @Autowired
    private FatherService fatherService;
    @GetMapping("/father")
    public List<Father> getAllFathers(){
        return fatherService.getAllFathers();
    }

    @GetMapping("/father/{Id}")
    public Father getFather(@PathVariable int Id){
        return fatherService.getFather(Id);
    }

    @PostMapping("/father")
    public String createFather(AddFatherRequest addFatherRequest){
        String fatherName = addFatherRequest.getFatherName();
        String[] kidName = addFatherRequest.getKidName();
        return fatherService.createFather(fatherName, kidName);
    }

    @PutMapping("/father/{Id}")
    public String updateFather(@PathVariable int Id, UpdateFatherRequest updateFatherRequest){
        String fatherName = updateFatherRequest.getFatherName();
        String[] kidName = updateFatherRequest.getKidName();
        return fatherService.updateFather(Id, fatherName, kidName);
    }

    @DeleteMapping("/father/{Id}")
    public String deleteFather(@PathVariable int Id){
        return fatherService.deleteFather(Id);
    }
}
