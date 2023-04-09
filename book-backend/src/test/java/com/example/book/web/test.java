package com.example.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {

	public static void main(String[] args) {
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		String encPassword = encode.encode("1234");
		System.out.println("user.getPassword() : "+encPassword);
	}
}
