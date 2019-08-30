package com.ehr.service;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {
	final Logger LOG = Logger.getLogger(this.getClass());
	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		LOG.debug("---------------------------------------");
		LOG.debug("-DummyMailSender-send-");
		LOG.debug("---------------------------------------");
	}

	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		LOG.debug("---------------------------------------");
		LOG.debug("-DummyMailSender-send-");
		LOG.debug("---------------------------------------");
	}
}
