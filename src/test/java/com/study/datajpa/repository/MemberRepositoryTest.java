package com.study.datajpa.repository;

import com.study.datajpa.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testMember(){
        Member member = new Member("테스트2_by Interface Repository");
        memberRepository.save(member);
        Member find = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(find).isEqualTo(member);
    }

    @Test
    @DisplayName("Create Test")
    public void basicC(){
        Member member = new Member("테스트");
        Member saved = memberRepository.save(member);
        Assertions.assertThat(saved).isEqualTo(member);
    }

    @Test
    @DisplayName("Read All Test")
    public void basicR_All() throws Exception{
        //given
        Member member1 = new Member("멤버1");
        Member member2 = new Member("멤버2");
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        //then
        Assertions.assertThat(memberRepository.findAll()).contains(member1, member2);
    }

    @Test
    @DisplayName("Update Test")
    public void basicU() throws Exception{
        //given
        Member member = new Member("테스트");
        memberRepository.save(member);
        //when
        String changedName = "변경명";
        member.changeUsername(changedName);
        Member findMember = memberRepository.save(member);
        //then
        Assertions.assertThat(findMember.getUsername()).isEqualTo(changedName);
    }

    @Test
    @DisplayName("Delete Test")
    public void basicD() throws Exception{
        //given
        Member member = new Member("삭제용");
        memberRepository.save(member);
        //when
        memberRepository.delete(member);
        //then
        Assertions.assertThat(memberRepository.findAll()).doesNotContain(member);
    }
}