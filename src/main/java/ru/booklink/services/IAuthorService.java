package ru.booklink.services;

import java.util.List;
import java.util.Optional;

import ru.booklink.models.Author;
import ru.booklink.models.Book;
import ru.booklink.models.PaginatedWrapper;

public interface IAuthorService {
	public Optional<Author> getAuthorByLogin(String login);

	public List<Author> getAllAuthors();

	public List<Book> getAuthorBooks(Author author, int startPosition, int maxResults, String sortFields,
			String sortDirections);

	public PaginatedWrapper<Book> getAuthorBooks(String login, PaginatedWrapper<Book> wrapper);

	public Author saveAuthor(Author author);

	public void deleteAuthor(Author author);
}
