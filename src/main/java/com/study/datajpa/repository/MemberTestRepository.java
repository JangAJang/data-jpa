package com.study.datajpa.repository;

import com.study.datajpa.domain.Member;
import com.study.datajpa.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberTestRepository {

    @PersistenceContext
    private EntityManager em;

    //Create
    public Member saveMember(Member member){
        em.persist(member);
        return member;
    }

    //Read One
    public Optional<Member> findMember(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    //Read All
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    //Read Count
    public Long countMember(){
        return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
    }

    //Update
    public Member updateMember(String name, int age, Team team){
        return null;
    }

    //Delete
    public void deleteMember(Long id){
        em.remove(em.find(Member.class, id));
    }




}
