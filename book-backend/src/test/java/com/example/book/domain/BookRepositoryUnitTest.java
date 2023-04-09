package com.example.book.domain;


import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.reposiroy.BookRepository;

//단위테스트 (DB관련된 Bena이 IOC에 등록되면됨)

@Transactional
@AutoConfigureTestDatabase(replace = Replace.ANY) //가짜 디비로 테스트 . Replace.None 실제DB테스트
@DataJpaTest//Repository들을 다 ioc등록해줌
public class BookRepositoryUnitTest {

	@Autowired
	private BookRepository bookRepository;
}
