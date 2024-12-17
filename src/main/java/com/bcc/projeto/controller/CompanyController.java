
package com.bcc.projeto.controller;

import com.bcc.projeto.entities.Category;
import com.bcc.projeto.entities.Company;
import com.bcc.projeto.entities.enums.Roles;
import com.bcc.projeto.exceptions.CNPJAlreadyInUseException;
import com.bcc.projeto.exceptions.EmailAlreadyInUseException;
import com.bcc.projeto.services.CategoryService;
import com.bcc.projeto.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService service;
    private final CategoryService categoryService;

    @Autowired
    public CompanyController(CompanyService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }


    @GetMapping
    public ResponseEntity<Page<Company>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "4") Integer linesPerPage,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy) {
        try {
            Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
            Pageable pageRequest = PageRequest.of(page, linesPerPage, sortDirection, orderBy);

            return ResponseEntity.ok().body(service.findAll(pageRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Company> FindById(@PathVariable Long id) {
        Company obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Page<Company>> findByCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "4") Integer linesPerPage,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy) {
        try {
            Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
            Pageable pageRequest = PageRequest.of(page, linesPerPage, sortDirection, orderBy);
            Category obj = categoryService.findById(id);
            return ResponseEntity.ok().body(service.findByCategory(obj.getId(), pageRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Company obj) {
        try {
            obj.setRole(Roles.ENTERPRISE);
            obj = service.insert(obj);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
            return ResponseEntity.created(uri).body(obj);
        } catch (EmailAlreadyInUseException | CNPJAlreadyInUseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Company> update(@PathVariable Long id,@RequestBody Company obj){
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
