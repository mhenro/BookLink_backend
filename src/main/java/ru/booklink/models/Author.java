package ru.booklink.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "author")
public class Author implements Serializable {
	private static final long serialVersionUID = 6083965748691707569L;

	private Integer authorId;
	private String name;
	private String surname;
	private String login;
	private String password;
	private LocalDate birthday;
	// List<AccessRight> rights; // rights for access

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "author_id", unique = true, nullable = false)
	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "surname", nullable = false, length = 100)
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Column(name = "login", nullable = false, unique = true, length = 100)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "password", nullable = false, length = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "birthdate", nullable = true)
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	/*
	@ManyToMany(fetch = FetchType.LAZY)
	public List<AccessRight> getRights() {
		if (rights == null) {
			rights = new ArrayList<>();
		}
		return rights;
	}
	
	public void setRights(List<AccessRight> rights) {
		this.rights = rights;
	}
	*/
}
