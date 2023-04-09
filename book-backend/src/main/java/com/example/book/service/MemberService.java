package com.example.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.domain.Member;
import com.example.book.reposiroy.MemberRepository;
import com.example.book.security.jwt.JwtTokenProvider;
import com.example.book.security.jwt.TokenInfo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
 
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    
    @Autowired 
    MemberRepository memberRepository;
    
    @Autowired 
    private BCryptPasswordEncoder passwordEncoder;
 
    @Transactional
    public TokenInfo login(String memberId, String password) throws AuthenticationException {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
 
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        // CustomAuthProvider => authenticate 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
 
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
 
        return tokenInfo;
    }
    
    @Transactional
    public String save(Member member) {
    	String raw = member.getPassword();
    	String en = passwordEncoder.encode(member.getPassword());
    	
    	member.setPassword(en);
        memberRepository.save(member);
        
    	return "ok";
	}
    
    @Transactional
    public String findById(Member member) {
    	//아이디로 검색 없으면 ok
    	//있으면 no
        
    	return "ok";
	}    
}
