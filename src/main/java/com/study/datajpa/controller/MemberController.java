package com.study.datajpa.controller;

import com.study.datajpa.domain.Member;
import com.study.datajpa.domain.Team;
import com.study.datajpa.dto.MemberDto;
import com.study.datajpa.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("members/{id}")
    public String findMember(@PathVariable("id")Long id){
        memberRepository.save(new Member("member", 10));
        return memberRepository.findById(id).get().getUsername();
    }
    @GetMapping("members2/{id}")
    public String findMember2(@PathVariable("id")Member member){
        return member.getUsername();
    }

    //pageable일 때, url에서 localhost:8080/members?page=페이지넘버&size=페이지의사이즈 로 호출할 수 있다.
    //@PageableDefault(size = 페이지당 사이즈)를 넣어주면 한 페이지당 개수를 정해줄 수 있다.
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable pageable){
        return memberRepository.findAll(pageable).map(member -> new MemberDto(member));
    }

    @PostConstruct
    public void init(){
        for(int index = 1; index <=1000; index++){
            memberRepository.save(new Member("member"+index, index));
        }
    }
}
