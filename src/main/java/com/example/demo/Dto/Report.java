package com.example.demo.Dto;

import lombok.Data;

@Data
public class Report {
    private int userId;
    private String userAccount;
    private String userPassword;
    private String userName;
    private int userPhone;
    private String userEmail;
    private String roles;

    public Report(int userId, String userAccount, String userPassword,
                  String userName, int userPhone, String userEmail, String roles) {
        this.userId = userId;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.roles = roles;
    }
}
