package com.example.book.reposiroy;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String username);
}
