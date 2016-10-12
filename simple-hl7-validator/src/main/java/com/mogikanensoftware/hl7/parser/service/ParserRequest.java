package com.mogikanensoftware.hl7.parser.service;

import java.nio.charset.Charset;

public interface ParserRequest {

	String getMessage();

	Charset getCharset();
	
	SupportedVersion getVersion();
}
