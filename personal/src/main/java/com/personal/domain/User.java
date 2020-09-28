package com.personal.domain;

import lombok.Data;

@Data
public class User {
	
    private int seq;
    private String id;
    private String password;
    private boolean enabled;

}
