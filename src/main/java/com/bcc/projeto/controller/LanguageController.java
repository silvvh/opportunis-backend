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

import com.bcc.projeto.entities.Language;
import com.bcc.projeto.services.LanguageService;

@RestController
public class LanguageController {

	@Autowired
	private LanguageService languageService;
	
	@GetMapping(value = "/curriculumns/{curriculumId}/languages")
	public ResponseEntity<List<Language>> findAllbyCurriculumId(@PathVariable Long curriculumId) {
		List<Language> body = languageService.findAllByCurriculumId(curriculumId);
		return ResponseEntity.ok().body(body);
	}
	
	@PostMapping(value = "/curriculumns/{curriculumId}/languages")
	public ResponseEntity<Language> insertByCurriculumId(@PathVariable Long curriculumId, @RequestBody Language obj) {
		Language body = languageService.insertByCurriculumId(curriculumId, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(body.getId()).toUri();
		return ResponseEntity.created(uri).body(body);
	}
	
	@PutMapping(value = "/curriculumns/languages/{id}")
	public ResponseEntity<Language> update(@PathVariable Long id, @RequestBody Language obj) {
		Language body = languageService.update(id, obj);
		return ResponseEntity.ok().body(body);
	}
	
	@DeleteMapping(value = "/curriculumns/languages/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		languageService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
