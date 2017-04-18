package ru.booklink.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

import ru.booklink.models.Author;

@Singleton
public class ActiveUsers {
	final static Logger log = Logger.getLogger(ActiveUsers.class.getName());
	private final Map<String, Author> activeUsers = new HashMap<>();

	@Schedule(persistent = false, minute = "*/10", hour = "*")
	public void clearActiveUsers() {
		// log.info("User list was cleared");
		activeUsers.clear();
	}

	public Author getAuthor(String token) {
		return activeUsers.get(token);
	}

	public Author removeAuthor(String token) {
		return activeUsers.remove(token);
	}

	public void saveAuthor(String token, Author author) {
		activeUsers.put(token, author);
	}
}
