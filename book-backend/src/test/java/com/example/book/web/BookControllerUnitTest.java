package com.example.book.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.example.book.domain.Book;
import com.example.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

//단위 테스트 (Controller 관련 로직만 띄우기) Filter,ControllerAdvice 

@Slf4j
@WebMvcTest
public class BookControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean //IOC 환경에 가짜 bean 등록됨.
	private BookService bookService;
	
	//BDDMockito 패턴 given,when,then 
	public void save_테스트() throws Exception {
		//given(테스트를 하기 위한 준비)
		Book book = new Book(1L, "스프링 따라하기", "");
		String content = new ObjectMapper().writeValueAsString(book);
		when(bookService.save(book)).thenReturn(new Book(1L, "스프링 따라하기", "")); //스터브 가짜
		
		//when (테스트 실행) //HTTP POST 요청
		ResultActions actions = mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then(검증) 실행한 결과 확인
		actions.andExpect(status().isCreated())
			   .andExpect(jsonPath("$.title").value("스프링 따라하기"))
			   .andDo(MockMvcResultHandlers.print());
	}
	
	public void findAll_테스트() throws Exception {
		
		//스터브 데이터 만들기
		List<Book> list = new ArrayList<>();
		list.add(new Book(1L, "1스프링 따라하기", ""));
		list.add(new Book(2L, "2스프링 따라하기", ""));
		
		//given
		when(bookService.list()).thenReturn(list); //가짜 결과 데이터 만들기
		
		// when
		ResultActions actions = mockMvc.perform(get("/book")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		
		// then(검증) 실행한 결과 확인
		actions.andExpect(status().isOk())
			   .andExpect(jsonPath("$", Matchers.hasSize(2)))
			   .andExpect(jsonPath("$.[0].title").value("1스프링 따라하기"))
			   .andDo(MockMvcResultHandlers.print());
		
		
	}
	
	public void findById_테스트() throws Exception {
		
		Long id = 1L;
		//given
		when(bookService.detail(id)).thenReturn(new Book(1L, "1스프링 따라하기", "")); //가짜 결과 데이터 만들기
		
		// when
		ResultActions actions = mockMvc.perform(get("/book/{id}",id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then(검증) 실행한 결과 확인
		actions.andExpect(status().isOk())
			   .andExpect(jsonPath("$.title").value("1스프링 따라하기"))
			   .andDo(MockMvcResultHandlers.print());
		
		
	}
	
	public void update_테스트() throws Exception {
		
		Long id = 1L;
		
		//given(테스트를 하기 위한 준비)
		Book book = new Book(1L, "c++ 따라하기", "");
		String content = new ObjectMapper().writeValueAsString(book);
		when(bookService.update(id,book)).thenReturn(new Book(1L, "c++ 따라하기", "")); //스터브 가짜
		
		//when (테스트 실행) //HTTP POST 요청
		ResultActions actions = mockMvc.perform(put("/book/{id}",id)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then(검증) 실행한 결과 확인
		actions.andExpect(status().isOk())
			   .andExpect(jsonPath("$.title").value("c++ 따라하기"))
			   .andDo(MockMvcResultHandlers.print());
		
		
	}
	public void delete_테스트() throws Exception {
		
		Long id = 1L;
		when(bookService.delete(id)).thenReturn("ok"); //스터브 가짜
		
		//when (테스트 실행) //HTTP POST 요청
		ResultActions actions = mockMvc.perform(delete("/book/{id}",id)
				.accept(MediaType.TEXT_PLAIN));
		
		// then(검증) 실행한 결과 확인
		actions.andExpect(status().isOk())			  
			   .andDo(MockMvcResultHandlers.print());
		
		MvcResult reqMvcResult =actions.andReturn();
		String result = reqMvcResult.getResponse().getContentAsString();
		
		assertEquals("ok", result);
		
	}	
}