package com.example.book.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.book.security.jwt.JwtAuthenticationFilter;
import com.example.book.security.jwt.JwtTokenProvider;
import com.example.book.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
 
    private final JwtTokenProvider jwtTokenProvider;
	
    /*
     *   1. httpBasic().disable().csrf().disable(): rest api이므로 basic auth 및 csrf 보안을 사용하지 않는다는 설정이다.
     *   2. sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS): JWT를 사용하기 때문에 세션을 사용하지 않는다는 설정이다.
     *   3. antMatchers().permitAll(): 해당 API에 대해서는 모든 요청을 허가한다는 설정이다.
     *   4. antMatchers().hasRole("USER"): USER 권한이 있어야 요청할 수 있다는 설정이다.
     *   5. anyRequest().authenticated(): 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정이다.
     *   6. addFilterBefore(new JwtAUthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class): JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행하겠다는 설정이다.
     *   7. passwordEncoder: JWT를 사용하기 위해서는 기본적으로 password encoder가 필요한데, 여기서는 Bycrypt encoder를 사용했다.
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/members/login").permitAll()
                //.antMatchers("/members/test").hasRole("USER1")
                .antMatchers("/book/").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
 
    /* 패스워드 인코딩 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    	return new BCryptPasswordEncoder();
    }
}
