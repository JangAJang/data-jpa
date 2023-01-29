package com.study.datajpa.repository;

import com.study.datajpa.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Member saveMember(Member member){
        em.persist(member);
        return member;
    }

    public Member findMember(Long id){
        return em.find(Member.class, id);
    }


}
