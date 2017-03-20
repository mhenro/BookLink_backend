package ru.booklink.services;

import java.util.List;
import java.util.Optional;

import ru.booklink.models.Book;
import ru.booklink.models.PaginatedWrapper;

public interface IBookService {
	public List<Book> getBooks();

	public List<Book> getBooks(int startPosition, int maxResults, String sortFields, String sortDirections);

	public PaginatedWrapper<Book> getBooks(PaginatedWrapper<Book> wrapper);

	public Optional<Book> getBookById(int id);

	public String getTextOfBook(int id);

	public Book saveBook(Book book);

	public void deleteBook(Book book);
}
