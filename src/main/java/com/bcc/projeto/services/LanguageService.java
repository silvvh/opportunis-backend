package com.bcc.projeto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcc.projeto.entities.Curriculum;
import com.bcc.projeto.entities.Language;
import com.bcc.projeto.exceptions.DatabaseException;
import com.bcc.projeto.exceptions.ResourceNotFoundException;
import com.bcc.projeto.repositories.CurriculumRepository;
import com.bcc.projeto.repositories.LanguageRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LanguageService {
	
	@Autowired
	private LanguageRepository languageRepo;
	
	@Autowired
	private CurriculumRepository curriculumRepo;
	
	public List<Language> findAllByCurriculumId(Long curriculumId) {
		Curriculum curr;
		
		try {
			curr = curriculumRepo.findById(curriculumId);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
		
		List<Language> academicBackgroundList = languageRepo.findByCurriculum(curr);
		return academicBackgroundList;
	}
	
	@Transactional
	public Language insertByCurriculumId(Long curriculumId, Language obj) {
		try {
			Curriculum curr = curriculumRepo.findById(curriculumId);
			obj.setCurriculum(curr);
			Language body = languageRepo.save(obj);
			return body;
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
	}
	
	@Transactional
	public Language update(Long id, Language newObj) {
		try {
			Language obj = languageRepo.findById(id).orElseThrow();
			
			obj.setLanguage(newObj.getLanguage());
			obj.setLevel(newObj.getLevel());
			
			return languageRepo.save(obj);
			
		 } catch(EntityNotFoundException e) {
			 throw new ResourceNotFoundException(id);
		 }	 
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			languageRepo.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
