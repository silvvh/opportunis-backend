package com.bcc.projeto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcc.projeto.entities.Curriculum;
import com.bcc.projeto.entities.Skill;


public interface SkillRepository extends JpaRepository<Skill, Long> {
	
	List<Skill> findByCurriculum(Curriculum curriculum);
}
