package com.bcc.projeto.controller;

import com.bcc.projeto.entities.Administrator;
import com.bcc.projeto.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdministratorService service;

    @GetMapping
    public ResponseEntity<List<Administrator>> findAll() {
        List<Administrator> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Administrator> FindById(@PathVariable Long id) {
        Administrator obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Administrator> insert(@RequestBody Administrator obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Administrator> update(@PathVariable Long id,@RequestBody Administrator obj){
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}