package com.mogikanensoftware.hl7.parser.service;

public interface SimpleParser {

	ParserResult parse(String message, SupportedVersion version) throws ParserException;
}
