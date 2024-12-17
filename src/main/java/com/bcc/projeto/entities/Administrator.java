package com.bcc.projeto.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_adm")
@PrimaryKeyJoinColumn(name = "adm_id")
public class Administrator extends User {

    @Serial
    private static final long serialVersionUID = 1L;

    private String cpf;
    private char genre;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;;
    public Administrator() {
    }

    public Administrator(Long id, String name, String email, String telephone, String password, String cpf, char genre, LocalDate birthDate) {
        super(id, name, email, telephone, password);
        this.cpf = cpf;
        this.genre = genre;
        this.birthDate = birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public char getGenre() {
        return genre;
    }

    public void setGenre(char genre) {
        this.genre = genre;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(birthDate, cpf, genre);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Administrator other = (Administrator) obj;
        return Objects.equals(birthDate, other.birthDate) && Objects.equals(cpf, other.cpf) && genre == other.genre;
    }

    @Override
    public String toString() {
        return "Candidate [cpf=" + cpf + ", genre=" + genre + ", birthDate=" + birthDate + "]";
    }
}
