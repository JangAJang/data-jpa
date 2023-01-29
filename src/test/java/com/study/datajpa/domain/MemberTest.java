package com.study.datajpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testTeamChange(){
        Team team1 = new Team("팀1");
        Team team2 = new Team("팀2");
        em.persist(team1);
        em.persist(team2);

        Member member1 = new Member("멤버1", 10, team1);
        Member member2 = new Member("멤버2", 10, team1);
        Member member3 = new Member("멤버3", 10, team2);
        Member member4 = new Member("멤버4", 10, team2);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        for (Member member : em.createQuery("select m from Member m", Member.class).getResultList()) {
            System.out.println("member : " + member);
            System.out.println("member.team : " + member.getTeam());
        }
    }
}