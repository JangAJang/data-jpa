package com.study.datajpa.repository;

import com.study.datajpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username = :username and m.age > :age")
    List<Member> findUser(@Param("username")String username, @Param("age") int age);

    //값타입 조회하기
    @Query("select m.username from Member m")
    List<String> findUsernameList();
}
