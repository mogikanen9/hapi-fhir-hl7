package com.mogikanensoftware.hl7.parser.service;

public interface SimpleParser {

	ParserResponse parse(ParserRequest request) throws ParserException;
}
