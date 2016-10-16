package com.mogikanensoftware.hl7.parser.service.impl;

import java.nio.charset.Charset;

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

import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;

public class SimpleParserImplTest extends BaseTest{

	private static final Logger logger = LogManager.getLogger(SimpleParserImplTest.class);
	
	private SimpleParser simpleParser;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void before(){
		simpleParser = new SimpleParserImpl(new CanonicalModelClassFactory("2.4"),new SimpleCustomValidationBuilder());
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
	public void testParse() throws Exception {
		
		String simpleOruMsgv24 = readFileAsString("/msg/hl7/v24/Simple-Msg-ORU-V24.HL7");
		logger.info(String.format("simpleOruMsgv24->\n%s",simpleOruMsgv24));
		
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
	public void testParseADTWithCustomZDR() throws Exception {
		
		String simpleAdtMsgv24 = readFileAsString("/msg/hl7/v24/Simple-Msg-ADT-V24.HL7");
		logger.info(String.format("simpleAdtMsgv24->\n%s",simpleAdtMsgv24));
		
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
	
	
	@Test
	public void testCustomValidation4MissignSFIdValue() throws Exception {
		
		thrown.expect(ParserException.class);
        thrown.expectMessage("Validation failed");
        
		String invalidAdtMsgv24 = readFileAsString("/msg/hl7/v24/Missing-MSH4-Msg-ADT-V24.HL7");
		logger.info(String.format("invalidAdtMsgv24->\n%s",invalidAdtMsgv24));
		
		simpleParser.parse(new ParserRequestImpl(invalidAdtMsgv24, SupportedVersion.v24,Charset.forName(UTF_8)));
		
		
	}
}
