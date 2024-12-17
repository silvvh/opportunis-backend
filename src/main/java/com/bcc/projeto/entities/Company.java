package com.bcc.projeto.entities;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_company")
@PrimaryKeyJoinColumn(name = "company_id")
public class Company extends User {

	@Serial
	private static final long serialVersionUID = 1L;

	private String socialName;

	@Column(unique = true)
	private String cnpj;

	private int qtdEmployee;
	private String site;
	private String companySector;
	private String nationality;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "company")
	private List<Feedback> feedbacks = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<Vacancy> vacancies = new ArrayList<>();


	public Company() {}

	public Company(Long id, String name, String email, String telephone, String password,
				   String socialName, String cnpj, int qtdEmployee, String site, String companySector, String nationality) {
		super(id, name, email, telephone, password);
		this.socialName = socialName;
		this.cnpj = cnpj;
		this.qtdEmployee = qtdEmployee;
		this.site = site;
		this.companySector = companySector;
		this.nationality = nationality;
	}

	public String getSocialName() {
		return socialName;
	}

	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public int getQtdEmployee() {
		return qtdEmployee;
	}

	public void setQtdEmployee(int qtdEmployee) {
		this.qtdEmployee = qtdEmployee;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getCompanySector() {
		return companySector;
	}

	public void setCompanySector(String companySector) {
		this.companySector = companySector;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public List<Vacancy> getVacancies() {
		return vacancies;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@JsonIgnore
	public Category getCategory() {
		return category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cnpj, companySector, nationality, qtdEmployee, site, socialName);
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
		Company other = (Company) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(companySector, other.companySector)
				&& Objects.equals(nationality, other.nationality) && qtdEmployee == other.qtdEmployee
				&& Objects.equals(site, other.site) && Objects.equals(socialName, other.socialName);
	}

	@Override
	public String toString() {
		return "Company [socialName=" + socialName + ", cnpj=" + cnpj + ", qtdEmployee=" + qtdEmployee + ", site="
				+ site + ", companySector=" + companySector + ", nationality=" + nationality + "]";
	}
}