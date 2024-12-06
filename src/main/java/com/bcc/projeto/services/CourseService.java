package com.bcc.projeto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcc.projeto.entities.Course;
import com.bcc.projeto.entities.Curriculum;
import com.bcc.projeto.exceptions.DatabaseException;
import com.bcc.projeto.exceptions.ResourceNotFoundException;
import com.bcc.projeto.repositories.CourseRepository;
import com.bcc.projeto.repositories.CurriculumRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private CurriculumRepository curriculumRepo;
	
	public List<Course> findAllByCurriculumId(Long curriculumId) {
		Curriculum curr;
		
		try {
			curr = curriculumRepo.findById(curriculumId);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
		
		List<Course> academicBackgroundList = courseRepo.findByCurriculum(curr);
		return academicBackgroundList;
	}
	
	@Transactional
	public Course insertByCurriculumId(Long curriculumId, Course obj) {
		try {
			Curriculum curr = curriculumRepo.findById(curriculumId);
			obj.setCurriculum(curr);
			Course body = courseRepo.save(obj);
			return body;
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
	}
	
	@Transactional
	public Course update(Long id, Course newObj) {
		try {
			Course obj = courseRepo.findById(id).orElseThrow();
			
			obj.setOrganization(newObj.getOrganization());
			obj.setDescription(newObj.getDescription());
			obj.setDateBegin(newObj.getDateBegin());
			obj.setDateEnd(newObj.getDateEnd());
			
			return courseRepo.save(obj);
			
		 } catch(EntityNotFoundException e) {
			 throw new ResourceNotFoundException(id);
		 }	 
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			courseRepo.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
