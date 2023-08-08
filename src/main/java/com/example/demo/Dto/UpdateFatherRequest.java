package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFatherRequest {
    private String fatherName;
    private String[] kidName;
}
