package com.ontariomd.hl7.service.message.bean;

public class MessageRequest extends AbstractBean {

	private static final long serialVersionUID = 1L;

	private String body;
	
	private String encoding;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
