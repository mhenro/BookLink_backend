package ru.booklink.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "author")
public class Author implements Serializable {
	private static final long serialVersionUID = 6083965748691707569L;

	private Integer authorId;
	private String avatarUrl;
	private String name;
	private String surname;
	private String login;
	private String password;
	private String city;
	private LocalDate birthday;
	private List<Book> books;
	private String portalName;
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

	@Column(name = "avatar_url", nullable = true, length = 200)
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
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

	@Column(name = "city", nullable = true, length = 100)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "birthdate", nullable = true)
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Column(name = "portal_name", nullable = false)
	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
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
