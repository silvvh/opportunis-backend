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

import com.bcc.projeto.entities.AcademicBackground;
import com.bcc.projeto.services.AcademicBackgroundService;

@RestController
public class AcademicBackgroundController {

	@Autowired
	private AcademicBackgroundService academicBackgroundService;
	
	@GetMapping(value = "/curriculumns/{curriculumId}/academic-backgrounds")
	public ResponseEntity<List<AcademicBackground>> findAllbyCurriculumId(@PathVariable Long curriculumId) {
		List<AcademicBackground> body = academicBackgroundService.findAllByCurriculumId(curriculumId);
		return ResponseEntity.ok().body(body);
	}
	
	@PostMapping(value = "/curriculumns/{curriculumId}/academic-backgrounds")
	public ResponseEntity<AcademicBackground> insertByCurriculumId(@PathVariable Long curriculumId, @RequestBody AcademicBackground obj) {
		AcademicBackground body = academicBackgroundService.insertByCurriculumId(curriculumId, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(body.getId()).toUri();
		return ResponseEntity.created(uri).body(body);
	}
	
	@PutMapping(value = "/curriculumns/academic-backgrounds/{id}")
	public ResponseEntity<AcademicBackground> update(@PathVariable Long id, @RequestBody AcademicBackground obj) {
		AcademicBackground body = academicBackgroundService.update(id, obj);
		return ResponseEntity.ok().body(body);
	}
	
	@DeleteMapping(value = "/curriculumns/academic-backgrounds/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		academicBackgroundService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
