package ru.booklink.utils;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import ru.booklink.models.Author;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Inject
	ActiveUsers activeUsers;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		/* get the HTTP header */
		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		/* check if the HTTP header is presented and formatted correctly */
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Authorization header must be provided");
		}

		/* try to extract the token from the HTTP header */
		String token = authHeader.substring("Bearer ".length()).trim();

		try {
			/* validating the token */
			validateToken(token);
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void validateToken(String token) throws Exception {
		Author author = activeUsers.getAuthor(token);
		if (author == null) {
			throw new Exception("Token is not valid");
		}
	}
}
