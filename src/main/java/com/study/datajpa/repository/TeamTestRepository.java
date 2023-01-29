package com.study.datajpa.repository;

import com.study.datajpa.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamTestRepository {

    @PersistenceContext
    private EntityManager em;

    //Create
    public Team saveTeam(Team team){
        em.persist(team);
        return team;
    }

    //Read One
    public Optional<Team> findTeam(Long id){
        Team team = em.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    //Read All
    public List<Team> findAll(){
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }

    //Read count
    public Long countTeam(){
        return em.createQuery("select count(t) from Team t", Long.class).getSingleResult();
    }

    //Update
    public Team updateTeam(){
        return null;
    }

    //Delete
    public void deleteTeam(Long id){
        Team team = em.find(Team.class, id);
        em.remove(team);
    }
}
