package com.bcc.projeto.entities;

import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_candidate")
@PrimaryKeyJoinColumn(name = "candidate_id")
public class Candidate extends User {

	@Serial
	private static final long  serialVersionUID = 1L;

	@Column(unique = true)
	private String cpf;

	private char genre;

	@OneToMany(mappedBy = "id.candidate")
	private final List<Candidature> candidatures = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private final List<Feedback> feedbacks = new ArrayList<>();
	
	@OneToMany(mappedBy = "candidate")
	private List<Curriculum> curriculumns = new ArrayList<>();	
	private Integer professionalExperienceQtd;
	private String url_image;

	public Candidate() {}

	public Candidate(Long id, String name, String email, String telephone, String password, String cpf, char genre, String url_image) {
		super(id, name, email, telephone, password);
		this.cpf = cpf;
		this.genre = genre;
		this.professionalExperienceQtd = 0;
		this.url_image = url_image;
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

	public String getUrl(){
		return url_image;
	}

	public void setUrl(String url){
		this.url_image = url;
	}

	@JsonIgnore
	public List<Vacancy> getVacancies() {
		List<Vacancy> vacancies = new ArrayList<>();
		for (Candidature c : candidatures) {
			vacancies.add(c.getVacancy());
		}
		return vacancies;
	}

	@JsonIgnore
	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	@JsonIgnore
	public List<Curriculum> getCurriculumns() {
		return curriculumns;
	}
	
	public Integer getProfessionalExperienceQtd() {
		return this.professionalExperienceQtd == null ? 0 : this.professionalExperienceQtd;
	}
	
	public void setProfessionalExperienceQtd(Integer qtd) {
		this.professionalExperienceQtd = qtd;
	}
	
	public void addProfessionalExperienceQtd() {
		this.professionalExperienceQtd += 1;
	}
	
	public void subProfessionalExperienceQtd() {
		this.professionalExperienceQtd -= 1;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cpf, genre);
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
		Candidate other = (Candidate) obj;
		return Objects.equals(cpf, other.cpf) && genre == other.genre;
	}

	@Override
	public String toString() {
		return "Candidate [cpf=" + cpf + ", genre=" + genre + "]";
	}
}
