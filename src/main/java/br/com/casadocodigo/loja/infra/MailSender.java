package br.com.casadocodigo.loja.infra;

import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.omg.IOP.Encoding;

public class MailSender {
	
	@Resource(mappedName="java:jboss/mail/gmail")
	private Session session;
	
	public void send(String from, String to, String subject, String body) {
		Message message = new MimeMessage(session);
		Charset.forName("UTF-8").encode(body);
		Charset.forName("UTF-8").encode(subject);
		try {
			message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);
			message.setContent(body, "text/html");
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
}
