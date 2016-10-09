package com.mogikanensoftware.hl7.parser.service.impl;

import com.mogikanensoftware.hl7.parser.service.ParserRequest;
import com.mogikanensoftware.hl7.parser.service.SupportedVersion;

public class PraserRequestImpl implements ParserRequest {

	private String message;
	
	private SupportedVersion version;
	
	
	public PraserRequestImpl(String message, SupportedVersion version) {
		super();
		this.message = message;
		this.version = version;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public SupportedVersion getVersion() {
		return version;
	}

}
