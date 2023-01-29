package com.study.datajpa.dto;

import lombok.Getter;

@Getter
public class MemberDto {

    private Long id;
    private String name;
    private String teamMane;

    public MemberDto(Long id, String name, String teamMane) {
        this.id = id;
        this.name = name;
        this.teamMane = teamMane;
    }
}
