package ru.booklink.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Auth {
	@XmlElement
	public String login;

	@XmlElement
	public String password;
}
