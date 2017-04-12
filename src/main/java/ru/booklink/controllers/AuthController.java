package ru.booklink.controllers;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.booklink.models.Auth;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {
	private SecureRandom random = new SecureRandom();

	@POST
	public String authUser(Auth auth) {
		try {
			// authenticate the user using the credentials provided
			authenticate(auth.login, auth.password);

			// issue a token for the user
			String token = issueToken(auth.login);

			return token;
		} catch (Exception e) {
			// return Response.status(Response.Status.UNAUTHORIZED).build();
			return "401";
		}
	}

	private void authenticate(String username, String password) throws Exception {
		/*TODO:
		 	Authenticate against a database, LDAP, file or whatever
		 	Throw an Exception if the credentials are invalid
		 */
	}

	private String issueToken(String username) {
		/*TODO
			Issue a token (can be a random String persisted to a database or a JWT token)
			The issued token must be associated to a user
			Return the issued token*/

		return new BigInteger(130, random).toString(32);
	}
}
