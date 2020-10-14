package com.personal.domain;

import lombok.Data;

@Data
public class User {
	
    private int userId;
    private String userName;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String tel;
    private String sex;
    private String birthDate;
    private String loginDate;
    private String joinDate;
    private boolean enabled;

}
