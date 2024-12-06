package com.bcc.projeto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcc.projeto.entities.Course;
import com.bcc.projeto.entities.Curriculum;


public interface CourseRepository extends JpaRepository<Course, Long> {
	
	List<Course> findByCurriculum(Curriculum curriculum);
}
