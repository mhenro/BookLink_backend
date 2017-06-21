package ru.booklink.controllers;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.booklink.models.Auth;
import ru.booklink.models.Author;
import ru.booklink.models.HelperError;
import ru.booklink.models.TextPacket;
import ru.booklink.services.IAuthorService;
import ru.booklink.utils.ActiveUsers;
import ru.booklink.utils.ITokenGenerator;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {
	private final int TOKEN_LENGTH = 130;

	@Inject
	IAuthorService authorService;

	@Inject
	ActiveUsers activeUsers;

	@Inject
	ITokenGenerator tokenGenerator;

	@POST
	public Response authUser(Auth auth) {
		try {
			// authenticate the user using the credentials provided
			authenticate(auth.login, auth.password);

			// issue a token for the user
			String token = issueToken(auth.login);

			return Response.ok(TextPacket.of(token)).build();
		} catch (Exception e) {
			HelperError error = new HelperError();
			error.message = "wrong credentials";
			return Response.status(Response.Status.UNAUTHORIZED).entity(TextPacket.of(error)).build();
		}
	}

	private void authenticate(String username, String password) throws Exception {
		Author test = authorService.getAuthorByLogin(username).orElse(null);
		if (test == null || !test.getPassword().equals(password)) {
			throw new Exception();
		}
	}

	private String issueToken(String username) {
		String newToken = tokenGenerator.issueToken(TOKEN_LENGTH);
		activeUsers.saveAuthor(newToken, authorService.getAuthorByLogin(username).orElse(null));

		return newToken;
	}
}
