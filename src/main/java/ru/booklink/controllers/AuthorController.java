package ru.booklink.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.booklink.models.Author;
import ru.booklink.models.Book;
import ru.booklink.models.HelperError;
import ru.booklink.models.PaginatedWrapper;
import ru.booklink.models.TextPacket;
import ru.booklink.services.IAuthorService;

@Path("/AuthorController")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorController {
	final static Logger log = Logger.getLogger(AuthorController.class.getName());

	@Inject
	private IAuthorService authorService;

	@GET
	@Path("/authors")
	public Response getAuthors() {
		try {
			List<Author> authors = authorService.getAllAuthors();
			authors.parallelStream().forEach(author -> author.setPassword(""));

			return Response.status(Response.Status.OK).entity(authors).build();
		} catch (Exception e) {
			HelperError error = new HelperError();
			error.message = "author not found";
			return Response.status(Response.Status.OK).entity(TextPacket.of(error)).build();
		}
	}

	@GET
	@Path("/authors/{login}")
	public Response getAuthor(@PathParam("login") final String login) {
		try {
			Author author = authorService.getAuthorByLogin(login).orElseThrow(NoSuchElementException::new);
			author.setPassword("");

			return Response.status(Response.Status.OK).entity(author).build();
		} catch (Exception e) {
			HelperError error = new HelperError();
			error.message = "author not found";
			return Response.status(Response.Status.OK).entity(TextPacket.of(error)).build();
		}
	}

	@GET
	@Path("/books/{login}")
	public Response getBooks(@PathParam("login") final String login,
			@DefaultValue("1") @QueryParam("page") Integer page,
			@DefaultValue("bookId") @QueryParam("sortFields") String sortFields,
			@DefaultValue("asc") @QueryParam("sortDirections") String sortDirections,
			@DefaultValue("10") @QueryParam("pageSize") Integer pageSize) {
		try {
			if (pageSize > 50) { // limit the maximum of pages
				pageSize = 50;
			}
			PaginatedWrapper<Book> wrapper = new PaginatedWrapper<>();
			wrapper.setCurrentPage(page);
			wrapper.setSortFields(sortFields);
			wrapper.setSortDirections(sortDirections);
			wrapper.setPageSize(pageSize);
			PaginatedWrapper<Book> books = authorService.getAuthorBooks(login, wrapper);

			return Response.status(Response.Status.OK).entity(books).build();
		} catch (Exception e) {
			HelperError error = new HelperError();
			error.message = e.getMessage();
			return Response.status(Response.Status.OK).entity(TextPacket.of(error)).build();
		}
	}
}
