package ru.booklink.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ejb.Stateless;

@Stateless
public class TokenGenerator implements ITokenGenerator {

	private final SecureRandom random = new SecureRandom();

	public String issueToken(int length) {
		String newToken = new BigInteger(length, random).toString(32);
		return newToken;
	}
}
