package com.study.datajpa.repository;

import com.study.datajpa.domain.Member;
import com.study.datajpa.domain.Team;
import com.study.datajpa.dto.MemberDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

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

    @Test
    @DisplayName("이름이 같고 나이가 크거나 같은 경우 조회")
    public void findByUsernameAndAgeGreaterThenTest() throws Exception{
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        //then
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("나이와 이름으로 찾기")
    public void findUserTest() throws Exception{
        //given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> result = memberRepository.findUser("AAA", 15);

        //then
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("")
    public void findUsernameTest() throws Exception{
        //given
        Member member1 = new Member("AAA");
        Member member2 = new Member("BBB");
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<String> names = memberRepository.findUsernameList();
        //then
        Assertions.assertThat(names.get(0)).isEqualTo("AAA");
        Assertions.assertThat(names.get(1)).isEqualTo("BBB");
    }

    @Test
    @DisplayName("")
    public void findMemberDtoTest() throws Exception{
        //given
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member member1 = new Member("AAA");
        member1.changeTeam(team);
        memberRepository.save(member1);

        //when
        List<MemberDto> dtos = memberRepository.findMemberDto();
        //then
        for (MemberDto dto : dtos) {
            System.out.println(dto);
            Assertions.assertThat(dto.getId()).isEqualTo(member1.getId());
            Assertions.assertThat(dto.getName()).isEqualTo(member1.getUsername());
            Assertions.assertThat(dto.getTeamMane()).isEqualTo(team.getName());
        }
    }

    @Test
    @DisplayName("")
    public void paging() throws Exception{
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        int age = 10;
        PageRequest request = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        //when
        Page<Member> result = memberRepository.findByAge(age, request);
        Page<MemberDto> dtos = result.map(m-> new MemberDto(m.getId(), m.getUsername(), m.getTeam().getName()));
        //then
        Assertions.assertThat(result.getContent().size()).isEqualTo(3);
        Assertions.assertThat(result.getTotalElements()).isEqualTo(5);
    }

    @Test
    @DisplayName("")
    public void bulkUpdate() throws Exception{
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));
        //when
        int resultCount = memberRepository.bulkAgePlus(20);

        //then
        Assertions.assertThat(resultCount).isEqualTo(2);
    }

    @Test
    @DisplayName("")
    public void fetchJoinTest() throws Exception{
        //given
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        teamRepository.save(team1);
        teamRepository.save(team2);

        memberRepository.save(new Member("member1", 10, team1));
        memberRepository.save(new Member("member1", 10, team2));

        em.flush();
        em.clear();
        //when
        List<Member> members = memberRepository.findEntityGraphByUsername("member1");
        //then
        for (Member member : members) {
            System.out.println(member.toString());
            System.out.println(member.getTeam().getName());
        }
    }

    @Test
    @DisplayName("")        
    public void queryHint() throws Exception{
        //given
        Member member = new Member("member1", 10);
        memberRepository.save(member);
        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findReadOnlyByUsername(member.getUsername());
        findMember.changeUsername("member2");
        em.flush();
        em.clear();
        //then
        Member dbMember = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(findMember.getUsername()).isNotEqualTo(dbMember.getUsername());
    }

    @Test
    @DisplayName("")
    public void customRepositoryTest() throws Exception{
        //given
        for(int index = 0; index < 5; index++){
            Member member = new Member("username"+index, index+10);
            memberRepository.save(member);
        }
        //when
        List<Member> originalRepositoryResult = memberRepository.findAll();
        List<Member> customRepositoryResult = memberRepository.findMemberCustom();
        //then
        Assertions.assertThat(originalRepositoryResult).isEqualTo(customRepositoryResult);
    }

    @Test
    @DisplayName("")
    public void createAndUpdateDateTest() throws Exception{
        //given
        Member member = new Member("Member1", 10);
        memberRepository.save(member);
        Thread.sleep(100);

        member.changeUsername("member2");

        em.flush();
        em.clear();
        //when
        Member find = memberRepository.findById(member.getId()).get();

        //then
        System.out.println("created at " + find.getCreateDate());
        System.out.println("updated at " + find.getUpdateDate());
        Assertions.assertThat(find.getCreateDate()).isNotEqualTo(find.getUpdateDate());
    }
}