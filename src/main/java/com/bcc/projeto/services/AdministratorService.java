package com.bcc.projeto.services;

import java.util.List;
import java.util.Optional;

import com.bcc.projeto.entities.Administrator;
import org.springframework.data.domain.Pageable;
import com.bcc.projeto.exceptions.*;
import com.bcc.projeto.repositories.AdmRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bcc.projeto.entities.Administrator;
import com.bcc.projeto.exceptions.DatabaseException;
import com.bcc.projeto.exceptions.ResourceNotFoundException;
import com.bcc.projeto.repositories.AdmRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AdministratorService {

    private final AdmRepository repository;

    @Autowired
    public AdministratorService(AdmRepository repository) {
        this.repository = repository;
    }

    public Page<Administrator> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Administrator findById(Long id) {
        Optional<Administrator> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }
    @Transactional
    public Administrator insert(Administrator obj){return repository.save(obj);}

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("EmptyResultDataAccessException caught. Throwing ResourceNotFoundException.");
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public Administrator update(Long id, Administrator obj) {
        try {
            Administrator entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Administrator entity, Administrator obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setTelephone(obj.getTelephone());
        entity.setBirthDate(obj.getBirthDate());
        entity.setCpf(obj.getCpf());
        entity.setPassword(obj.getPassword());
    }

}