package com.bcc.projeto.dtos;


import com.bcc.projeto.entities.Candidate;
import com.bcc.projeto.entities.enums.Roles;

import java.time.LocalDate;
import java.util.Date;

public record CandidateDTO(String name, String email, String telephone, String password, String cpf, char genre, LocalDate birthDate) {
    public CandidateDTO(Candidate candidate) {
        this(candidate.getName(), candidate.getEmail(), candidate.getTelephone(), candidate.getPassword(), candidate.getCpf(), candidate.getGenre(), candidate.getBirthDate());
    }
}
