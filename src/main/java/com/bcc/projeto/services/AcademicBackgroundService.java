package com.bcc.projeto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcc.projeto.entities.AcademicBackground;
import com.bcc.projeto.entities.Curriculum;
import com.bcc.projeto.exceptions.DatabaseException;
import com.bcc.projeto.exceptions.ResourceNotFoundException;
import com.bcc.projeto.repositories.AcademicBackgroundRepository;
import com.bcc.projeto.repositories.CurriculumRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AcademicBackgroundService {
	
	@Autowired
	private AcademicBackgroundRepository academicBackgroundRepo;
	
	@Autowired
	private CurriculumRepository curriculumRepo;
	
	public List<AcademicBackground> findAllByCurriculumId(Long curriculumId) {
		Curriculum curr;
		
		try {
			curr = curriculumRepo.findById(curriculumId);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
		
		List<AcademicBackground> academicBackgroundList = academicBackgroundRepo.findByCurriculum(curr);
		return academicBackgroundList;
	}
	
	@Transactional
	public AcademicBackground insertByCurriculumId(Long curriculumId, AcademicBackground obj) {
		try {
			Curriculum curr = curriculumRepo.findById(curriculumId);
			obj.setCurriculum(curr);
			AcademicBackground body = academicBackgroundRepo.save(obj);
			return body;
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
	}
	
	@Transactional
	public AcademicBackground update(Long id, AcademicBackground newObj) {
		try {
			AcademicBackground obj = academicBackgroundRepo.findById(id).orElseThrow();
			
			obj.setOrganization(newObj.getOrganization());
			obj.setDescription(newObj.getDescription());
			obj.setDateBegin(newObj.getDateBegin());
			obj.setDateEnd(newObj.getDateEnd());
			
			return academicBackgroundRepo.save(obj);
			
		 } catch(EntityNotFoundException e) {
			 throw new ResourceNotFoundException(id);
		 }	 
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			academicBackgroundRepo.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
