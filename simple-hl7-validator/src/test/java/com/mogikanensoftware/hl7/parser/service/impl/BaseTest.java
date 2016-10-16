package com.mogikanensoftware.hl7.parser.service.impl;

import java.io.IOException;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

public abstract class BaseTest {

	protected static final String UTF_8 = "UTF-8";
	
	protected String readFileAsString(String relativePath) throws IOException{
		return IOUtils.readLines(SimpleParserImplTest.class.getResourceAsStream(
				relativePath),UTF_8).stream().collect(Collectors.joining());
	}
}
