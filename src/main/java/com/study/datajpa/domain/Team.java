package com.study.datajpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TEAM")
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends JpaBasicEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "TEAM_NAME")
    private String name;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
