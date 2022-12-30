package com.paper.order.service.impl;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.paper.order.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Value("${smtp.port}")
	private String port;

	@Value("${smtp.host}")
	private String host;

	private JavaMailSender javaMailSender;

	@Override
	public void triggerEmail() {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom("nagendra_nallamilli@suchiit.com");
				helper.setTo("nagendra_nallamilli@suchiit.com");
				helper.setSubject("Test Email");
				helper.setText("Testing content inside email");
			}
		};
		this.getJavaMailSender().send(preparator);
	}

	public JavaMailSender getJavaMailSender() {
		if (this.javaMailSender == null) {
			JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
			javaMailSenderImpl.setHost(this.host);
			javaMailSenderImpl.setUsername("nagendra_nallamilli@suchiit.com");
			javaMailSenderImpl.setPassword("");
			javaMailSenderImpl.setPort(Integer.parseInt(this.port));
			javaMailSenderImpl.setJavaMailProperties(this.getMailProperties());
			this.javaMailSender = javaMailSenderImpl;
		}
		return this.javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.put("mail.debug", "true");
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.connectiontimeout", "21600000");
		properties.put("mail.smtp.timeout", "21600000");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		return properties;
	}
}
