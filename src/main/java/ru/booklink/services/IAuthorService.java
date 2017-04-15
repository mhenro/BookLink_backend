package ru.booklink.services;

import java.util.Optional;

import ru.booklink.models.Author;

public interface IAuthorService {
	public Optional<Author> getAuthorByLogin(String login);

	public Author saveAuthor(Author author);

	public void deleteAuthor(Author author);
}
