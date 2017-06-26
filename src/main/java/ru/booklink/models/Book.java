package ru.booklink.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book implements Serializable {
	private static final long serialVersionUID = -8094831815485063053L;
	private Integer bookId;
	private String name;
	private Author author;
	private List<Comment> comments;
	private String annotation;
	private String text;
	private Integer likes;
	private LocalDate lastUpdate;
	private LocalDate created;
	private Style style;
	private Genre genre;

	public Book() {

	}

	/* constructor for creating short book descriptions for lists */
	public Book(Integer bookId, String name, String annotation, Integer likes, LocalDate lastUpdate, Style style,
			Genre genre, Author author) {
		this.bookId = bookId;
		this.name = name;
		this.annotation = annotation;
		this.likes = likes;
		this.lastUpdate = lastUpdate;
		this.style = style;
		this.genre = genre;
		this.author = author;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id", unique = true, nullable = false)
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	@Column(name = "book_name", nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id", nullable = false)
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
	public List<Comment> getComments() {
		if (comments == null) {
			comments = new ArrayList<>();
		}
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	@Column(name = "text", length = 10485760) // up to 10Mb
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public LocalDate getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDate lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
}
