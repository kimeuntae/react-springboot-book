package com.example.book.domain;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
	 private String memberId;
	 private String password;
}
