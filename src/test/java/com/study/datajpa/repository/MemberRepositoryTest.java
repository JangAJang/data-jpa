package com.study.datajpa.repository;

import com.study.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testMember(){
        Member member = new Member("테스트");
        Member saved = memberRepository.saveMember(member);
        Assertions.assertThat(saved).isEqualTo(member);
    }

}