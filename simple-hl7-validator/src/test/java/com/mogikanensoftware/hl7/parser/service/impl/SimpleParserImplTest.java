package com.mogikanensoftware.hl7.parser.service.impl;



import java.io.IOException;
import java.nio.charset.Charset;
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

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;

public class SimpleParserImplTest {

	private static final String UTF_8 = "UTF-8";

	private static final Logger logger = LogManager.getLogger(SimpleParserImplTest.class);
	
	private String simpleOruMsgv24;
	
	private String simpleAdtMsgv24;
	
	private SimpleParser simpleParser;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	public SimpleParserImplTest() throws IOException{
		init();
	}
	
	
	protected String readFileAsString(String relativePath) throws IOException{
		return IOUtils.readLines(SimpleParserImplTest.class.getResourceAsStream(
				relativePath),UTF_8).stream().collect(Collectors.joining());
	}
	
	public void init() throws IOException{
	
		simpleOruMsgv24 = readFileAsString("/msg/hl7/v24/Simple-Msg-ORU-V24.HL7");
		logger.info(String.format("simpleOruMsgv24->\n%s",simpleOruMsgv24));
		
		simpleAdtMsgv24 = readFileAsString("/msg/hl7/v24/Simple-Msg-ADT-V24.HL7");
		logger.info(String.format("simpleAdtMsgv24->\n%s",simpleAdtMsgv24));
		
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
		simpleParser.parse(new ParserRequestImpl("invalid hl7v2 message", SupportedVersion.v24, Charset.forName(UTF_8)));
	}
	
	@Test
	public void testParse() throws ParserException {
		ParserResponse result = simpleParser.parse(new ParserRequestImpl(simpleOruMsgv24, SupportedVersion.v24,Charset.forName(UTF_8)));
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
	
	@Test
	public void testParseADTWithCustomZDR() throws ParserException, HL7Exception {
		ParserResponse result = simpleParser.parse(new ParserRequestImpl(simpleAdtMsgv24, SupportedVersion.v24,Charset.forName(UTF_8)));
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getMessage());
		
		Assert.assertTrue(result.getMessage() instanceof ADT_A01);
		ADT_A01 parsedMessage = (ADT_A01)result.getMessage();
		logger.info(String.format("parsedMessage ADT_A01 -> %s \r", parsedMessage));
		
		//messag header is not null
		Assert.assertNotNull(parsedMessage.getMSH());
		
		Segment pidSegment = (Segment)parsedMessage.get("PID");
		Assert.assertNotNull(pidSegment);
		logger.info(String.format("PID->%s", pidSegment));
		
	}
}
