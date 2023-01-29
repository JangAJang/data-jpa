package com.study.datajpa.repository;

import com.study.datajpa.domain.Member;
import com.study.datajpa.dto.MemberDto;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("select new com.study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query(countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    //Slice<Member> findByAge(int age, Pageable pageable); 이런식으로 했을 때, 여분까지 미리 뽑아온다. 또한, 이렇게 했을 때 불러오는 부분에서도 Slice<>형으로 받아주어야 한다.

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age > :age")
    int bulkAgePlus(@Param("age")int age);

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username")String username);
}
