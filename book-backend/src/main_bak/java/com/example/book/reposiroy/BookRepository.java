package com.example.book.reposiroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
