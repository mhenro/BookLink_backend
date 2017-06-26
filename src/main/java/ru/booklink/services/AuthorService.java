package ru.booklink.services;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.booklink.models.Author;
import ru.booklink.models.Book;
import ru.booklink.models.PaginatedWrapper;

@Stateless
public class AuthorService implements IAuthorService {
	@PersistenceContext(unitName = "entityManager")
	private EntityManager em;

	@Override
	public Optional<Author> getAuthorByLogin(String login) {
		Author author = null;
		try {
			author = em.createQuery("SELECT a FROM Author a where a.login = :login", Author.class)
					.setParameter("login", login).getSingleResult();
		} catch (NoResultException e) {
			/* no need */
		}

		return Optional.ofNullable(author);
	}

	@Override
	public List<Author> getAllAuthors() {
		try {
			return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
		} catch (NoResultException e) {
			/* no need*/
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getAuthorBooks(Author author, int startPosition, int maxResults, String sortFields,
			String sortDirections) {
		try {
			Query q = em.createQuery(
					"SELECT b FROM Book b WHERE b.author = :author ORDER BY b." + sortFields + " " + sortDirections,
					Book.class).setParameter("author", author);
			q.setFirstResult(startPosition);
			q.setMaxResults(maxResults);

			return q.getResultList();
		} catch (NoResultException e) {
			/* no need*/
		}
		return Collections.emptyList();
	}

	private Integer countBooks(Author author) {
		Query q = em.createQuery("SELECT COUNT(b.bookId) FROM Book b WHERE b.author = :author").setParameter("author",
				author);
		return ((Long) q.getSingleResult()).intValue();
	}

	private Integer getPageCount(int totalResults, int pageSize) {
		return (totalResults / pageSize) + (totalResults % pageSize > 0 ? 1 : 0);
	}

	@Override
	public PaginatedWrapper<Book> getAuthorBooks(String login, PaginatedWrapper<Book> wrapper) {
		Author author = getAuthorByLogin(login).orElseThrow(NoSuchElementException::new);
		wrapper.setTotalResults(countBooks(author));
		wrapper.setPageCount(getPageCount(wrapper.getTotalResults(), wrapper.getPageSize()));
		int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
		wrapper.setList(getAuthorBooks(author, start, wrapper.getPageSize(), wrapper.getSortFields(),
				wrapper.getSortDirections()));
		return wrapper;
	}

	@Override
	public Author saveAuthor(Author author) {
		Author result = null;
		if (author.getAuthorId() == null) {
			em.persist(author);
			result = author;
		} else {
			result = em.merge(author);
		}

		return result;
	}

	@Override
	public void deleteAuthor(Author author) {
		em.remove(author);
	}

}
