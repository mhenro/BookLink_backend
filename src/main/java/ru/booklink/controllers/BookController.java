package ru.booklink.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ru.booklink.models.Book;
import ru.booklink.models.JsonOb;
import ru.booklink.models.PaginatedWrapper;
import ru.booklink.services.IBookService;
import ru.booklink.utils.Secured;

@Path("/BookController")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookController {
	final static Logger log = Logger.getLogger(BookController.class.getName());

	@Inject
	private IBookService bookService;

	@GET
	@Path("/books")
	public PaginatedWrapper<Book> booksGet(@DefaultValue("1") @QueryParam("page") Integer page,
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
			return bookService.getBooks(wrapper);
		} catch (Exception e) {
			log.severe(e.toString());
		}
		return null;
	}

	@GET
	@Path("/books/{id}")
	public Book bookGet(@PathParam("id") final Integer id) {
		try {
			final Optional<Book> book = bookService.getBookById(id);
			return book.orElse(null);
		} catch (Exception e) {
			log.severe(e.toString());
		}
		return null;
	}

	@POST
	@Path("/books")
	public Book bookSave(Book book) {
		return bookService.saveBook(book);
	}

	/*
	@PUT
	@Path("/books/{id}")
	public Book bookPut(@PathParam("id") final int id, Book book) {
		try {
			// book = bookService.
		} catch (Exception e) {
			log.severe(e.toString());
		}
		return book;
	}
	*/

	@DELETE
	@Path("/books/{id}")
	public void bookDelete(@PathParam("id") final Integer id) {
		try {
			final Optional<Book> book = bookService.getBookById(id);
			if (book.isPresent()) {
				bookService.deleteBook(book.get());
			}
		} catch (Exception e) {
			log.severe(e.toString());
		}
	}

	// ======================================TEST=========================

	@GET
	@Secured
	@Path("/getJson")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonOb getJson() {
		List<String> testArr = Arrays.asList("olooo", "trololo", "sss", "bbb");
		JsonOb ob = new JsonOb();
		ob.setText("ololo");
		ob.setValue(111);
		ob.setData(testArr);
		log.info("Log this!");
		return ob;
	}
}