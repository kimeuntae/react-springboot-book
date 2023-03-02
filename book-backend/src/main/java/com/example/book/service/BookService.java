package com.example.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class BookService {

	@Autowired
	private final BookRepository bookRepository;
	///함수 => 송금 -> 레파지토리에 여러개의 함수 실행 -> commit or rollback
	
	@Transactional //서비스 함수가 종료될때 commit 할지 rollback할지 트랜잭션 관리하겠다.
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	//다른데서 update치더라도 유지
	@Transactional(readOnly = true) //JPA 변경감지라는 내부 기능 활성화 X,  update시의 정함성을 유지해줌 , insert의 유령데이터 현상 못막음
	public Book detail(Long id) {
		return bookRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("상세보기 실패");
		});
	}
	
	@Transactional(readOnly = true)
	public List<Book> list() {
		return bookRepository.findAll();
	}
	
	@Transactional
	public Book update(Long id ,Book book) {
		
		Book bookEntity = bookRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id찾기 실패");
		});
		//영속화
		bookEntity.setTitle(book.getTitle());
		bookEntity.setAuthor(book.getAuthor());
		
		return bookEntity;
	}
	
	public String delete(Long id) {
		bookRepository.deleteById(id);
		return "ok";
	}	
}
