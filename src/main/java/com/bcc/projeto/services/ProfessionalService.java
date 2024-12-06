package com.bcc.projeto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcc.projeto.entities.Curriculum;
import com.bcc.projeto.entities.Professional;
import com.bcc.projeto.exceptions.DatabaseException;
import com.bcc.projeto.exceptions.ResourceNotFoundException;
import com.bcc.projeto.repositories.CurriculumRepository;
import com.bcc.projeto.repositories.ProfessionalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {
	
	@Autowired
	private ProfessionalRepository professionalRepo;
	
	@Autowired
	private CurriculumRepository curriculumRepo;
	
	public List<Professional> findAllByCurriculumId(Long curriculumId) {
		Curriculum curr;
		
		try {
			curr = curriculumRepo.findById(curriculumId);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
		
		List<Professional> professionalList = professionalRepo.findByCurriculum(curr);
		return professionalList;
	}
	
	@Transactional
	public Professional insertByCurriculumId(Long curriculumId, Professional professional) {
		try {
			Curriculum curr = curriculumRepo.findById(curriculumId);
			professional.setCurriculum(curr);
			Professional body = professionalRepo.save(professional);
			return body;
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(curriculumId);
		}
	}
	
	@Transactional
	public Professional update(Long id, Professional newObj) {
		try {
			Professional obj = professionalRepo.findById(id).orElseThrow();
			
			obj.setOrganization(newObj.getOrganization());
			obj.setDescription(newObj.getDescription());
			obj.setDateBegin(newObj.getDateBegin());
			obj.setDateEnd(newObj.getDateEnd());
			
			return professionalRepo.save(obj);
			
		 } catch(EntityNotFoundException e) {
			 throw new ResourceNotFoundException(id);
		 }	 
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			professionalRepo.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
