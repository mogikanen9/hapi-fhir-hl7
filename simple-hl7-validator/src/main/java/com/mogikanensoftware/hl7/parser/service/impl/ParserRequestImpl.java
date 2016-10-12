package com.mogikanensoftware.hl7.parser.service.impl;

import java.nio.charset.Charset;

import com.mogikanensoftware.hl7.parser.service.ParserRequest;
import com.mogikanensoftware.hl7.parser.service.SupportedVersion;

public class ParserRequestImpl implements ParserRequest {

	private String message;
	
	private SupportedVersion version;
	
	private Charset charset;
	
	
	public ParserRequestImpl(String message, SupportedVersion version, Charset charset) {
		super();
		this.message = message;
		this.version = version;
		this.charset = charset;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public SupportedVersion getVersion() {
		return version;
	}

	@Override
	public Charset getCharset() {
		return charset;
	}

}
