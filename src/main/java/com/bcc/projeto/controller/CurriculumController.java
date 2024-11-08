package com.bcc.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcc.projeto.entities.Curriculum;
import com.bcc.projeto.repositories.CurriculumRepository;

@RestController
@RequestMapping("/curriculum")
public class CurriculumController {

	// TODO é necessario criar a camada de Service
	@Autowired
	private CurriculumRepository curriculumRepo;
	
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Curriculum>> findAll() {
		
		List<Curriculum> allCurriculumns = curriculumRepo.findAll();
		
		return new ResponseEntity<List<Curriculum>>(allCurriculumns, HttpStatus.OK);
	}
}