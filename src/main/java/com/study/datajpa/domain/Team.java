package com.study.datajpa.domain;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "TEAM")
@Data
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "TEAM_NAME")
    private String name;
}
