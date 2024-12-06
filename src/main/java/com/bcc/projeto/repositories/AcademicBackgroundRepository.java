package com.bcc.projeto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcc.projeto.entities.AcademicBackground;
import com.bcc.projeto.entities.Curriculum;

public interface AcademicBackgroundRepository extends JpaRepository<AcademicBackground, Long> {
	
	List<AcademicBackground> findByCurriculum(Curriculum curriculum);
}
