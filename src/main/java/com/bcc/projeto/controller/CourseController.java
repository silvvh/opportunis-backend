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

import com.bcc.projeto.entities.Course;
import com.bcc.projeto.services.CourseService;

@RestController
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping(value = "/curriculumns/{curriculumId}/courses")
	public ResponseEntity<List<Course>> findAllbyCurriculumId(@PathVariable Long curriculumId) {
		List<Course> body = courseService.findAllByCurriculumId(curriculumId);
		return ResponseEntity.ok().body(body);
	}
	
	@PostMapping(value = "/curriculumns/{curriculumId}/courses")
	public ResponseEntity<Course> insertByCurriculumId(@PathVariable Long curriculumId, @RequestBody Course obj) {
		Course body = courseService.insertByCurriculumId(curriculumId, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(body.getId()).toUri();
		return ResponseEntity.created(uri).body(body);
	}
	
	@PutMapping(value = "/curriculumns/courses/{id}")
	public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course obj) {
		Course body = courseService.update(id, obj);
		return ResponseEntity.ok().body(body);
	}
	
	@DeleteMapping(value = "/curriculumns/courses/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		courseService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
