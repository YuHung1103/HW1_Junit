package com.example.demo.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    @GetMapping("/report")
    public String userReport() {
        return userReportService.generateReport();
    }
}
