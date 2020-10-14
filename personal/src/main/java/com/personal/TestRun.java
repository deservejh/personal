package com.personal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestRun {
	
	public static void main(String[] args) {
		String password = "wnsgur12!@";
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.println(bCryptPasswordEncoder.encode(password));
		
		System.out.println(bCryptPasswordEncoder.matches(password, bCryptPasswordEncoder.encode(password)));
	}

}
