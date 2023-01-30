package com.study.datajpa.controller;

import com.study.datajpa.domain.Member;
import com.study.datajpa.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @GetMapping("/members")
    public Page<Member> list(Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    @PostConstruct
    public void init(){
        for(int index = 1; index <=1000; index++){
            memberRepository.save(new Member("member"+index, index));
        }
    }
}
