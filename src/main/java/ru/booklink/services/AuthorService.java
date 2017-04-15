package ru.booklink.services;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import ru.booklink.models.Author;

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
