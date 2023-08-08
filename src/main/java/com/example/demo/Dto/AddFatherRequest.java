package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFatherRequest {
    private String fatherName;
    private String[] kidName;
}
