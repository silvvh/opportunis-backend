package com.bcc.projeto.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bcc.projeto.entities.Skill;
import com.bcc.projeto.services.SkillService;

@RestController
public class SkillController {

	@Autowired
	private SkillService skillService;
	
	@GetMapping(value = "/curriculumns/{curriculumId}/skills")
	public ResponseEntity<List<Skill>> findAllbyCurriculumId(@PathVariable Long curriculumId) {
		List<Skill> body = skillService.findAllByCurriculumId(curriculumId);
		return ResponseEntity.ok().body(body);
	}
	
	@PostMapping(value = "/curriculumns/{curriculumId}/skills")
	public ResponseEntity<Skill> insertByCurriculumId(@PathVariable Long curriculumId, @RequestBody Skill obj) {
		Skill body = skillService.insertByCurriculumId(curriculumId, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(body.getId()).toUri();
		return ResponseEntity.created(uri).body(body);
	}
	
	@PutMapping(value = "/curriculumns/skills/{id}")
	public ResponseEntity<Skill> update(@PathVariable Long id, @RequestBody Skill obj) {
		Skill body = skillService.update(id, obj);
		return ResponseEntity.ok().body(body);
	}
	
	@DeleteMapping(value = "/curriculumns/skills/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		skillService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
