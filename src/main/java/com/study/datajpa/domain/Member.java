package com.study.datajpa.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "MEMBER_USERNAME")
    private String username;
    @Column(name = "MEMBER_AGE")
    private int age;

    protected Member() {}

    public Member(String username) {
        this.username = username;
    }

    public void changeUsername(String username){
        this.username = username;
    }
}
