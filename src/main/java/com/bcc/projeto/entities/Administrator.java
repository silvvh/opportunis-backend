package com.bcc.projeto.entities;

import com.bcc.projeto.entities.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_adm")
@PrimaryKeyJoinColumn(name = "adm_id")
public class Administrator extends Profile {

    private static final long serialVersionUID = 1L;

    private String cpf;
    private char genre;
    private Date birthDate;
    private final Integer role = Roles.Admin.ordinal();

    public Administrator() {
    }

    public Administrator(Long id, String name, String email, String telephone, String password, String cpf, char genre,
                     Date birthDate) {
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getRole() {return role; }
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
