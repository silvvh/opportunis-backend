package com.bcc.projeto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcc.projeto.entities.Curriculum;
import com.bcc.projeto.entities.Skill;
import com.bcc.projeto.exceptions.DatabaseException;
import com.bcc.projeto.exceptions.ResourceNotFoundException;
import com.bcc.projeto.repositories.CurriculumRepository;
import com.bcc.projeto.repositories.SkillRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SkillService {
	
	@Autowired
	private SkillRepository skillRepo;
	
	@Autowired
	private CurriculumRepository curriculumRepo;
	
	public List<Skill> findAllByCurriculumId(Long curriculumId) {
		Curriculum curr;
		
		try {
			curr = curriculumRepo.findById(curriculumId);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
		
		List<Skill> academicBackgroundList = skillRepo.findByCurriculum(curr);
		return academicBackgroundList;
	}
	
	@Transactional
	public Skill insertByCurriculumId(Long curriculumId, Skill obj) {
		try {
			Curriculum curr = curriculumRepo.findById(curriculumId);
			obj.setCurriculum(curr);
			Skill body = skillRepo.save(obj);
			return body;
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
	}
	
	@Transactional
	public Skill update(Long id, Skill newObj) {
		try {
			Skill obj = skillRepo.findById(id).orElseThrow();
			
			obj.setName(newObj.getName());
			
			return skillRepo.save(obj);
			
		 } catch(EntityNotFoundException e) {
			 throw new ResourceNotFoundException(id);
		 }	 
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			skillRepo.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
