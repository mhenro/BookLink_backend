package ru.booklink.services;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.booklink.models.Book;
import ru.booklink.models.PaginatedWrapper;

@Stateless
public class BookService implements IBookService {
	@PersistenceContext(unitName = "entityManager")
	private EntityManager em;

	private Integer countBooks() {
		Query q = em.createQuery("SELECT COUNT(b.bookId) FROM Book b");
		return ((Long) q.getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooks() {
		Query q = em.createQuery("from Book", Book.class);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooks(int startPosition, int maxResults, String sortFields, String sortDirections) {
		Query q = em.createQuery("SELECT b FROM Book b ORDER BY " + sortFields + " " + sortDirections);
		q.setFirstResult(startPosition);
		q.setMaxResults(maxResults);
		return q.getResultList();
	}

	private Integer getPageCount(int totalResults, int pageSize) {
		return (totalResults / pageSize) + (totalResults % pageSize > 0 ? 1 : 0);
	}

	@Override
	public PaginatedWrapper<Book> getBooks(PaginatedWrapper<Book> wrapper) {
		wrapper.setTotalResults(countBooks());

		wrapper.setPageCount(getPageCount(wrapper.getTotalResults(), wrapper.getPageSize()));
		int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
		wrapper.setList(getBooks(start, wrapper.getPageSize(), wrapper.getSortFields(), wrapper.getSortDirections()));
		return wrapper;
	}

	@Override
	public Optional<Book> getBookById(int id) {
		return Optional.ofNullable(em.find(Book.class, id));
	}

	@Override
	public Book saveBook(Book book) {
		Book result = null;
		if (book.getBookId() == null) {
			em.persist(book);
			result = book;
		} else {
			result = em.merge(book);
		}
		return result;
	}

	@Override
	public void deleteBook(Book book) {
		em.remove(book);
	}
}
