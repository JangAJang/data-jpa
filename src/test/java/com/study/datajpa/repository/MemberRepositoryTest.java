package com.study.datajpa.repository;

import com.study.datajpa.domain.Member;
import org.assertj.core.api.Assertions;
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
}