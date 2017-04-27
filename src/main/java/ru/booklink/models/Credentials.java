package ru.booklink.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Credentials {
	@XmlElement
	public String login;

	@XmlElement
	public String password;

	@XmlElement
	public String email;
}
