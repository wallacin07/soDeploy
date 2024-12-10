package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.HardSkill;

@Repository
public interface HardSkillRepository extends  JpaRepository<HardSkill, Long> {
    
}
