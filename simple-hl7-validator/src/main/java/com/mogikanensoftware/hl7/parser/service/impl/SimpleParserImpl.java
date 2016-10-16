package com.mogikanensoftware.hl7.parser.service.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.hl7.parser.service.ParserException;
import com.mogikanensoftware.hl7.parser.service.ParserRequest;
import com.mogikanensoftware.hl7.parser.service.ParserResponse;
import com.mogikanensoftware.hl7.parser.service.SimpleParser;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.llp.MinLowerLayerProtocol;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.validation.builder.support.DefaultValidationBuilder;

public class SimpleParserImpl implements SimpleParser {

	private static final Logger logger = LogManager.getLogger(SimpleParserImpl.class);

	private CanonicalModelClassFactory modelClassFactory;
	private DefaultValidationBuilder validationBuilder;
	
	
	
	public SimpleParserImpl(CanonicalModelClassFactory modelClassFactory, DefaultValidationBuilder validationBuilder) {
		super();
		this.modelClassFactory = modelClassFactory;
		this.validationBuilder = validationBuilder;
	}



	@Override
	public ParserResponse parse(ParserRequest request) throws ParserException {

		try (HapiContext context = new DefaultHapiContext()) {
			
			//set char set for message 
			MinLowerLayerProtocol mllp = new MinLowerLayerProtocol();
			mllp.setCharset(request.getCharset());
			context.setLowerLayerProtocol(mllp);
			
			// TODO - handle version properly
			context.setModelClassFactory(this.modelClassFactory);
			
			context.setValidationRuleBuilder(this.validationBuilder);
			
			//TODO - deal with executor and closeble
			context.getExecutorService();
			
			// Pass the MCF to the parser in its constructor
			PipeParser parser = context.getPipeParser();

			// The parser parses the v2.4 message
			Message message = parser.parse(request.getMessage());
			if(logger.isDebugEnabled()){
				logger.debug(String.format("message->%s", message));
			}
			
			
			ParserResponse result = () -> message;
			return result;
		} catch (IOException | HL7Exception e) {
			logger.error(e.getMessage(),e);
			throw new ParserException(e.getMessage(),e);
		}


	}

}
