package com.mogikanensoftware.hl7.parser.service.impl;

import org.apache.commons.lang.StringUtils;

import com.mogikanensoftware.hl7.parser.service.ParserException;
import com.mogikanensoftware.hl7.parser.service.ParserRequest;
import com.mogikanensoftware.hl7.parser.service.ParserResponse;
import com.mogikanensoftware.hl7.parser.service.SimpleParser;


public class SimpleParserDecorator implements SimpleParser {

	private SimpleParser origin;
	
	public SimpleParserDecorator(SimpleParser simpleParser){
		this.origin = simpleParser;
	}
	
	@Override
	public ParserResponse parse(ParserRequest request) throws ParserException {
		if(request==null){
			throw new ParserException("Parser Request is null.");
		}else if(StringUtils.isEmpty(request.getMessage())){
			throw new ParserException("Inboud message is empty or null.");
		}else if (request.getVersion()==null){
			throw new ParserException("Version is null.");
		}else if (request.getCharset()==null){
			throw new ParserException("Charset is null.");
		}else{
			return this.origin.parse(request);
		}
		
	}

}
