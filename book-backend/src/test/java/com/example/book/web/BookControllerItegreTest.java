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

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/*
 * 통함 테스트 (모든 Bean들을 똑같이 IOC올리고 테스트)
 * MOCK 실제 톰켓을 올리는게 아니라 다름 톰켓으로 테스트
 * RANDOM_PORT 실제 톰켓으로 테스트
 * @AutoConfigureMockMvc IOC
 *  
 * */
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BookControllerItegreTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BookRepository bookRepository;	
	
	@Autowired
	EntityManager entityManager;
	
	@BeforeEach //각각 테스트전에 실행
	public void init() {
		entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
	}	

	@Test
	public void save_테스트() throws Exception {
		//given(테스트를 하기 위한 준비)
		Book book = new Book(1L, "스프링 따라하기", "");
		String content = new ObjectMapper().writeValueAsString(book);
		
		log.info(content);
		//when (테스트 실행)
		ResultActions actions = mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		
		// then(검증)
		actions.andExpect(status().isCreated())
			   .andExpect(jsonPath("$.title").value("스프링 따라하기"))
			   .andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void findAll_테스트() throws Exception {
		
		//스터브 데이터 만들기
		List<Book> list = new ArrayList<>();
		list.add(new Book(1L, "1스프링 따라하기", ""));
		list.add(new Book(2L, "2스프링 따라하기", ""));
		bookRepository.saveAll(list);
		
		// when
		ResultActions actions = mockMvc.perform(get("/book")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then(검증) 실행한 결과 확인
		actions.andExpect(status().isOk())
			   .andExpect(jsonPath("$", Matchers.hasSize(2)))
			   .andExpect(jsonPath("$.[0].title").value("1스프링 따라하기"))
			   .andDo(MockMvcResultHandlers.print());
		
		
	}
	
	@Test
	public void findById_테스트() throws Exception {
		Long id = 1L;
		
		//스터브 데이터 만들기
		List<Book> list = new ArrayList<>();
		list.add(new Book(1L, "1스프링 따라하기", ""));
		list.add(new Book(2L, "2스프링 따라하기", ""));
		bookRepository.saveAll(list);
		
		// when
		ResultActions actions = mockMvc.perform(get("/book/{id}",id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then(검증) 실행한 결과 확인
		actions.andExpect(status().isOk())
			   .andExpect(jsonPath("$.title").value("1스프링 따라하기"))
			   .andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void update_테스트() throws Exception {
		
		Long id = 1L;
		//스터브 데이터 만들기
		List<Book> list = new ArrayList<>();
		list.add(new Book(null, "1스프링 따라하기", ""));
		list.add(new Book(null, "2스프링 따라하기", ""));
		bookRepository.saveAll(list);
		
		//given(테스트를 하기 위한 준비)
		Book book = new Book(1L, "c++ 따라하기", "");
		String content = new ObjectMapper().writeValueAsString(book);
		
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
	
	@Test
	public void delete_테스트() throws Exception {
		
		Long id = 1L;
		//스터브 데이터 만들기
		List<Book> list = new ArrayList<>();
		list.add(new Book(null, "1스프링 따라하기", ""));
		list.add(new Book(null, "2스프링 따라하기", ""));
		bookRepository.saveAll(list);
		
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
