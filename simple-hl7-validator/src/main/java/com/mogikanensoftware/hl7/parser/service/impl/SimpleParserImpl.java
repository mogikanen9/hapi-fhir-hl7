package com.mogikanensoftware.hl7.parser.service.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.hl7.parser.service.ParserException;
import com.mogikanensoftware.hl7.parser.service.ParserResult;
import com.mogikanensoftware.hl7.parser.service.SimpleParser;
import com.mogikanensoftware.hl7.parser.service.SupportedVersion;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;

public class SimpleParserImpl implements SimpleParser {

	private static final Logger logger = LogManager.getLogger(SimpleParserImpl.class);

	@Override
	public ParserResult parse(String message, SupportedVersion version) throws ParserException {

		HapiContext context = new DefaultHapiContext();

		//TODO handle version properly
		CanonicalModelClassFactory mcf = new CanonicalModelClassFactory("2.4");
		context.setModelClassFactory(mcf);

		// Pass the MCF to the parser in its constructor
		PipeParser parser = context.getPipeParser();

		// The parser parses the v2.3 message to a "v25" structure
		ca.uhn.hl7v2.model.v24.message.ORU_R01 msg;
		try {
			msg = (ca.uhn.hl7v2.model.v24.message.ORU_R01) parser.parse(message);
			ParserResult result = () -> msg;
			return result;
		} catch (HL7Exception e) {
			logger.error(e.getMessage(), e);
			throw new ParserException(e.getMessage(), e);
		} finally {
			if (context != null) {
				try {
					context.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					throw new ParserException(e.getMessage(), e);
				}
			}
		}

	}

}
