package com.mogikanensoftware.hl7.parser.service.impl;

import java.nio.charset.Charset;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mogikanensoftware.hl7.parser.service.ParserException;
import com.mogikanensoftware.hl7.parser.service.SimpleParser;
import com.mogikanensoftware.hl7.parser.service.SupportedVersion;


@RunWith(MockitoJUnitRunner.class)
public class SimpleParserDecoratorTest {

	@Mock
	private SimpleParser origin;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testNullRequest() throws ParserException {
		SimpleParser decoratedParser = new SimpleParserDecorator(origin);
		thrown.expect(ParserException.class);
        thrown.expectMessage("Parser Request is null");
		decoratedParser.parse(null);

	}
	
	@Test
	public void testNullOrEmptyMessage() throws ParserException {
		SimpleParser decoratedParser = new SimpleParserDecorator(origin);
		thrown.expect(ParserException.class);
        thrown.expectMessage("Inboud message is empty or null");
		decoratedParser.parse(new ParserRequestImpl(null, null,null));
		decoratedParser.parse(new ParserRequestImpl("", null,null));
	}

	@Test
	public void testNullVersion() throws ParserException {
		SimpleParser decoratedParser = new SimpleParserDecorator(origin);
		thrown.expect(ParserException.class);
        thrown.expectMessage("Version is null");
		decoratedParser.parse(new ParserRequestImpl("abc", null,null));
	}
	
	@Test
	public void testNullCharset() throws ParserException {
		SimpleParser decoratedParser = new SimpleParserDecorator(origin);
		thrown.expect(ParserException.class);
        thrown.expectMessage("Charset is null");
		decoratedParser.parse(new ParserRequestImpl("abc", SupportedVersion.v24,null));
	}
	
	@Test
	public void testAllValidaParams() throws ParserException {
		SimpleParser decoratedParser = new SimpleParserDecorator(origin);
		decoratedParser.parse(new ParserRequestImpl("abc", SupportedVersion.v24, Charset.forName("UTF-8")));
	}
}
