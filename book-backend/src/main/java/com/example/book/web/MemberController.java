package com.example.book.web;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book.domain.Member;
import com.example.book.domain.MemberLoginRequestDto;
import com.example.book.reposiroy.MemberRepository;
import com.example.book.security.jwt.TokenInfo;
import com.example.book.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
	
    private final MemberService memberService;
	
    MemberRepository memberRepository; 
 
    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }
    
    @PostMapping("/join")
    public String join(@RequestBody Member member) {
    	System.out.println("member.getPassword() : "  + member.getPassword());
        String val = memberService.save(member);
        return "ok";
    }    
    
    @PostMapping("/test")
    public String test() {
        return "success";
    }
    
}
