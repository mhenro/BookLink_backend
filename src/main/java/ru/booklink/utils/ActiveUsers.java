package ru.booklink.utils;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

import ru.booklink.models.Author;

@Singleton
public class ActiveUsers {
	private final Map<String, Author> activeUsers = new HashMap<>();

	@Schedule(persistent = false, second = "1")
	public void clearActiveUsers() {
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
