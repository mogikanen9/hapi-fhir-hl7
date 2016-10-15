package com.mogikanensoftware.hl7.parser.service.impl;

import ca.uhn.hl7v2.Version;
import ca.uhn.hl7v2.validation.builder.support.DefaultValidationBuilder;

public class SimpleCustomValidationBuilder extends DefaultValidationBuilder {

	private static final long serialVersionUID = 1L;

	@Override
	protected void configure() {
		super.configure();
		forVersion(Version.V24)
		  				    .message("ADT", "*")
		  				    .terser("MSH-4", not(empty()));
	}
	
	

}
