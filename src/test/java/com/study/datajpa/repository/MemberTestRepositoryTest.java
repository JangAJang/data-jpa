package com.study.datajpa.repository;

import com.study.datajpa.domain.Member;
import com.study.datajpa.domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTestRepositoryTest {

    @Autowired
    private MemberTestRepository memberTestRepository;

    @Test
    @DisplayName("Create Test")
    public void basicC(){
        Member member = new Member("테스트");
        Member saved = memberTestRepository.saveMember(member);
        Assertions.assertThat(saved).isEqualTo(member);
    }
    
    @Test
    @DisplayName("Read All Test")
    public void basicR_All() throws Exception{
        //given
        Member member1 = new Member("멤버1");
        Member member2 = new Member("멤버2");
        //when
        memberTestRepository.saveMember(member1);
        memberTestRepository.saveMember(member2);
        //then
        Assertions.assertThat(memberTestRepository.findAll()).contains(member1, member2);
    }

    @Test
    @DisplayName("Update Test")
    public void basicU() throws Exception{
        //given
        Member member = new Member("테스트");
        memberTestRepository.saveMember(member);
        //when
        String changedName = "변경명";
        member.changeUsername(changedName);
        Member findMember = memberTestRepository.findMember(member.getId()).get();
        //then
        Assertions.assertThat(findMember.getUsername()).isEqualTo(changedName);
    }

    @Test
    @DisplayName("Delete Test")
    public void basicD() throws Exception{
        //given
        Member member = new Member("삭제용");
        memberTestRepository.saveMember(member);
        //when
        memberTestRepository.deleteMember(member.getId());
        //then
        Assertions.assertThat(memberTestRepository.findAll()).doesNotContain(member);
    }
    
    @Test
    @DisplayName("이름이 같고 나이가 크거나 같은 경우 조회")
    public void findByUsernameAndAgeGreaterThenTest() throws Exception{
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberTestRepository.saveMember(member1);
        memberTestRepository.saveMember(member2);
        //when
        List<Member> result = memberTestRepository.findByUsernameAndAgeGreaterThen("AAA", 15);

        //then
        Assertions.assertThat(result.size()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("")        
    public void paging() throws Exception{
        //given
        memberTestRepository.saveMember(new Member("member1", 10));
        memberTestRepository.saveMember(new Member("member2", 10));
        memberTestRepository.saveMember(new Member("member3", 10));
        memberTestRepository.saveMember(new Member("member4", 10));
        memberTestRepository.saveMember(new Member("member5", 10));
        //when
        List<Member> result = memberTestRepository.findByPage(10, 0, 3);
        long totalCount = memberTestRepository.countTotal(10);
        //then
        Assertions.assertThat(result.size()).isEqualTo(3);
        Assertions.assertThat(memberTestRepository.countMember()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("")        
    public void bulkUpdate() throws Exception{
        //given
        memberTestRepository.saveMember(new Member("member1", 10));
        memberTestRepository.saveMember(new Member("member2", 19));
        memberTestRepository.saveMember(new Member("member3", 20));
        memberTestRepository.saveMember(new Member("member4", 21));
        memberTestRepository.saveMember(new Member("member5", 40));
        //when
        int resultCount = memberTestRepository.bulkAgePlus(20);

        //then
        Assertions.assertThat(resultCount).isEqualTo(2);
    }
}