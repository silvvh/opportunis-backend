package com.bcc.projeto.services;

import java.util.List;
import java.util.Optional;

import com.bcc.projeto.dtos.CompanyDTO;
import com.bcc.projeto.dtos.ResponseDTO;
import com.bcc.projeto.entities.*;
import com.bcc.projeto.entities.enums.Roles;
import com.bcc.projeto.exceptions.CNPJAlreadyInUseException;
import com.bcc.projeto.exceptions.DatabaseException;
import com.bcc.projeto.exceptions.EmailAlreadyInUseException;
import com.bcc.projeto.exceptions.ResourceNotFoundException;
import com.bcc.projeto.repositories.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bcc.projeto.entities.Company;
import com.bcc.projeto.exceptions.DatabaseException;
import com.bcc.projeto.exceptions.ResourceNotFoundException;
import com.bcc.projeto.repositories.CompanyRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository repository;
    private final CategoryService service;

    @Autowired
    public CompanyService(CompanyRepository repository, CategoryService service) {
        this.repository = repository;
        this.service = service;
    }

    public Page<Company> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Company findById(Long id) {
        Optional<Company> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public Company insert(Company obj) {
        return repository.save(obj);
    }

    @Transactional
    public void insert(CompanyDTO companyDTO, String encryptedPassword) {
        Company company = new Company();
        company.setName(companyDTO.name());
        company.setEmail(companyDTO.email());
        company.setCnpj(companyDTO.cnpj());
        company.setPassword(encryptedPassword);
        company.setTelephone(companyDTO.telephone());
        company.setRole(Roles.ENTERPRISE);
        company.setCompanySector(companyDTO.category());

        Optional<Company> existingByEmail = repository.findByEmailEquals(company.getEmail());
        if (existingByEmail.isPresent()) {
            throw new EmailAlreadyInUseException("Email já está em uso: " + company.getEmail());
        }

        Optional<Company> existingByCnpj = repository.findBycnpjEquals(company.getCnpj());
        if (existingByCnpj.isPresent()) {
            throw new CNPJAlreadyInUseException("CNPJ já está em uso: " + company.getCnpj());
        }
        repository.save(company);
    }

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
    public Company update(Long id, Company obj) {
        try {
            Company entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public ResponseDTO findByEmail(String email) {
        Optional<Company> companyOptional = repository.findByEmailEquals(email);
        Company company = companyOptional.orElseThrow(() -> new EntityNotFoundException("Company not found"));
        return new ResponseDTO(company.getEmail());
    }

    public Page<Company> findByCategory(Long categoryId, Pageable pageable) {
        Category category = service.findById(categoryId);
        return repository.findByCategory(category, pageable);
    }

    private void updateData(Company entity, Company obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setSocialName(obj.getSocialName());
        entity.setCnpj(obj.getCnpj());
        entity.setQtdEmployee(obj.getQtdEmployee());
        entity.setSite(obj.getSite());
        entity.setCompanySector(obj.getCompanySector());
        entity.setNationality(obj.getNationality());
    }

}