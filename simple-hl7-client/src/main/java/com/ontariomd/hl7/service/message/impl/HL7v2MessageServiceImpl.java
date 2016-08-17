package com.ontariomd.hl7.service.message.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ontariomd.hl7.service.message.MessageService;
import com.ontariomd.hl7.service.message.MessageServiceException;
import com.ontariomd.hl7.service.message.bean.MessageInfoBean;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;

public class HL7v2MessageServiceImpl implements MessageService {

	private static final Logger logger = LogManager.getLogger(HL7v2MessageServiceImpl.class);

	private String server;
	private int port;
	private boolean useTls;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isUseTls() {
		return useTls;
	}

	public void setUseTls(boolean useTls) {
		this.useTls = useTls;
	}

	public HL7v2MessageServiceImpl(String server, int port, boolean useTls) {
		super();
		this.server = server;
		this.port = port;
		this.useTls = useTls;
	}

	@Override
	public void send(MessageInfoBean message) throws MessageServiceException {
		HapiContext context = null;
		try {
			context = new DefaultHapiContext();						
			
			Parser p = context.getPipeParser();

			Message adt = p.parse(message.getData());

			// A connection object represents a socket attached to an HL7 server
			Connection connection = context.newClient(server, port, useTls);

			// The initiator is used to transmit unsolicited messages
			Initiator initiator = connection.getInitiator();
			Message response = initiator.sendAndReceive(adt);

			String responseString = p.encode(response);
			logger.info("Received response:\n" + responseString);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new MessageServiceException(e.getMessage(), e);
		} finally {
			if (context != null) {
				try {
					context.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					throw new MessageServiceException(e.getMessage(), e);
				}
			}
		}
	}

}
