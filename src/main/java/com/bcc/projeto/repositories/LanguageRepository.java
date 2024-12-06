package com.bcc.projeto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcc.projeto.entities.Curriculum;
import com.bcc.projeto.entities.Language;


public interface LanguageRepository extends JpaRepository<Language, Long> {
	
	List<Language> findByCurriculum(Curriculum curriculum);
}
