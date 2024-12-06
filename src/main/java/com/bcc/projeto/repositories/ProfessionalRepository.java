package com.bcc.projeto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcc.projeto.entities.Curriculum;
import com.bcc.projeto.entities.Professional;


public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

	List<Professional> findByCurriculum(Curriculum curriculum);
}
