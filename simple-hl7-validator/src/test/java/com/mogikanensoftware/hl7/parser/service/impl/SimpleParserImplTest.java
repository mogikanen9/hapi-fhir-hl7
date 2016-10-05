package com.mogikanensoftware.hl7.parser.service.impl;



import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.mogikanensoftware.hl7.parser.service.ParserException;
import com.mogikanensoftware.hl7.parser.service.ParserResult;
import com.mogikanensoftware.hl7.parser.service.SimpleParser;
import com.mogikanensoftware.hl7.parser.service.SupportedVersion;


public class SimpleParserImplTest {

	private String simpleOruMsgv24;
	
	public SimpleParserImplTest() throws IOException{
		
		List<String> content = IOUtils.readLines(SimpleParserImplTest.class.getResourceAsStream(
				"/msg/hl7/v24/Simple-Msg-ORU-V24.HL7"),"UTF-8");
		simpleOruMsgv24 = content.stream().collect(Collectors.joining());
	}
	
	@Test
	public void testParse() throws ParserException {
		SimpleParser simpleParser = new SimpleParserImpl();
		ParserResult result = simpleParser.parse(simpleOruMsgv24, SupportedVersion.v24);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getMessage());
	}

}
