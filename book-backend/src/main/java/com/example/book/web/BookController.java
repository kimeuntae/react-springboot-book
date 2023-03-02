package com.example.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.book.domain.Book;
import com.example.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BookController {

	@Autowired
	private final BookService bookService;
	
	//security(라이브러리 적용) - CORS 정책으로 가지고 있음
	@CrossOrigin
	@PostMapping("/book") //? 타입을 리턴해 따라 지정한다.
	public ResponseEntity<?> save(@RequestBody Book book) {
		return new ResponseEntity<>(bookService.save(book),HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/book")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(bookService.list(),HttpStatus.OK);
		
	}
	
	@CrossOrigin
	@GetMapping("/book/{id}")
	public ResponseEntity<?> detail(@PathVariable Long id) {
		return new ResponseEntity<>(bookService.detail(id),HttpStatus.OK);
		
	}
	
	@CrossOrigin
	@PutMapping("/book/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Book book) {
		return new ResponseEntity<>(bookService.update(id, book),HttpStatus.OK);
		
	}
	
	@CrossOrigin
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return new ResponseEntity<>(bookService.delete(id),HttpStatus.OK);
		
	}	
}
