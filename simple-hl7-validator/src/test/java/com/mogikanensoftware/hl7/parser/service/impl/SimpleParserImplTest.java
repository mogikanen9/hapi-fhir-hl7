package com.mogikanensoftware.hl7.parser.service.impl;



import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mogikanensoftware.hl7.parser.service.ParserException;
import com.mogikanensoftware.hl7.parser.service.ParserResponse;
import com.mogikanensoftware.hl7.parser.service.SimpleParser;
import com.mogikanensoftware.hl7.parser.service.SupportedVersion;

import ca.uhn.hl7v2.model.v24.message.ORU_R01;

public class SimpleParserImplTest {

	private static final Logger logger = LogManager.getLogger(SimpleParserImplTest.class);
	
	private String simpleOruMsgv24;
	
	private SimpleParser simpleParser;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	public SimpleParserImplTest() throws IOException{
		init();
	}
	
	
	public void init() throws IOException{
		List<String> content = IOUtils.readLines(SimpleParserImplTest.class.getResourceAsStream(
				"/msg/hl7/v24/Simple-Msg-ORU-V24.HL7"),"UTF-8");
		simpleOruMsgv24 = content.stream().collect(Collectors.joining());
		logger.info(String.format("simpleOruMsgv24->\n%s",simpleOruMsgv24));
	}
	
	
	@Before
	public void before(){
		simpleParser = new SimpleParserImpl();
	}
	
	public void after(){
		simpleParser = null;
	}
	
	@Test
	public void testParseInvalidMsg() throws ParserException {
		thrown.expect(ParserException.class);
        thrown.expectMessage("Determine encoding for message");
		simpleParser.parse(new PraserRequestImpl("invalid hl7v2 message", SupportedVersion.v24));
	}
	
	@Test
	public void testParse() throws ParserException {
		ParserResponse result = simpleParser.parse(new PraserRequestImpl(simpleOruMsgv24, SupportedVersion.v24));
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getMessage());
		
		Assert.assertTrue(result.getMessage() instanceof ORU_R01);
		ORU_R01 parsedMessage = (ORU_R01)result.getMessage();
		
		//messag header is not null
		Assert.assertNotNull(parsedMessage.getMSH());
		
		//SF is set
		Assert.assertNotNull(parsedMessage.getMSH().getMsh4_SendingFacility());
		logger.info(String.format("parsedMessage.getMSH().getMsh4_SendingFacility()->%s",parsedMessage.getMSH().getMsh4_SendingFacility().toString()));
		
		//SF is 4070
		Assert.assertEquals(parsedMessage.getMSH().getMsh4_SendingFacility().getNamespaceID().getValue(), "4070");
		
	}


}
