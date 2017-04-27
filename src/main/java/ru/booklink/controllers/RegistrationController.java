package ru.booklink.controllers;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.booklink.models.Author;
import ru.booklink.models.Credentials;
import ru.booklink.models.TextPacket;
import ru.booklink.models.exceptions.RegistrationFailException;
import ru.booklink.services.IAuthorService;

@Path("/registration")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationController {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Inject
	IAuthorService authorService;

	@POST
	public Response registration(Credentials cred) {
		try {
			if (cred.login.isEmpty() || cred.password.isEmpty() || cred.email.isEmpty()
					|| !isEmailCorrect(cred.email)) {
				throw new RegistrationFailException("wrong credentials");
			}
			Author author = authorService.getAuthorByLogin(cred.login).orElse(null);
			if (author != null) {
				throw new RegistrationFailException("author already exist");
			}

			sendEmail(cred.email);

			return Response.ok(TextPacket.of("success")).build();
		} catch (RegistrationFailException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(TextPacket.of(e)).build();
		}
	}

	/* function for checking email */
	private boolean isEmailCorrect(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	/* function to send email */
	private void sendEmail(String email) throws RegistrationFailException {
		String to = email;
		String from = "web@booklink.ru";
		Properties props = System.getProperties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", "localhost");
		props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.smtp.auth", "true");
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("bot", "4Book4Link");
			}
		};

		Session session = Session.getDefaultInstance(props, authenticator);

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject("Welcome to the BookLink!");
			msg.setContent(
					"Thank you for using our service! "
							+ "To complete the registration please follow to this link: <a href=\"#\">blablabla</a>",
					"text/html");
			Transport.send(msg);
		} catch (MessagingException e) {
			throw new RegistrationFailException("can't send the activation details to client's email");
		}
	}
}
