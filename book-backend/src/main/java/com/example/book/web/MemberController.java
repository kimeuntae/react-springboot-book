package com.example.book.web;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

@CrossOrigin
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
        TokenInfo tokenInfo = null;
        try {
        	tokenInfo = memberService.login(memberId, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        //로그인 실패시
        if(tokenInfo == null) {
        	tokenInfo = new TokenInfo();
        	tokenInfo.setMessage("error");
        }
        
        return tokenInfo;
    }
    
    @PostMapping("/join")
    public String join(@RequestBody Member member) {
    	//아이디 검색 함수 호출
    	//아이디 없으면 저장
        String val = memberService.save(member);
        return "ok";
    }    
    
    @GetMapping("/test")
    public String test1() {
        return "success";
    }
    
    @PostMapping("/test")
    public String test() {
        return "success";
    }
    
}
