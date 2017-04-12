package ru.booklink.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Author implements Serializable {
	private static final long serialVersionUID = 6083965748691707569L;

	private Integer authorId;
	private String name;
	private String password;
	private String surname;
	private LocalDate birthday;

}
