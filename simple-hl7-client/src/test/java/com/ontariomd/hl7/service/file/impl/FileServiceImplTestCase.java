package com.ontariomd.hl7.service.file.impl;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ontariomd.hl7.service.file.FileService;
import com.ontariomd.hl7.service.file.FileServiceException;

public class FileServiceImplTestCase {

	private static final Logger logger = LogManager.getLogger(FileServiceImplTestCase.class);
	
	@Test
	public void testRead() {
		FileService fileService = new FileServiceImpl();
		try {
			fileService.read(null, null);
			Assert.fail("Exception is expected");
		} catch (FileServiceException e) {
			logger.info(String.format("Exception of type %s raised. Error message -> %s.",e.getClass().toString(),e.getMessage()));
			Assert.assertTrue(e.getCause() instanceof NullPointerException);
		}
				
		File file = new File("/usr/opt/aaa/xxx/kakaziabla.txt");
		
		try {
			fileService.read(file, null);
		} catch (FileServiceException e) {
			logger.info(String.format("Exception of type %s raised. Error message -> %s.",e.getClass().toString(),e.getMessage()));			
			Assert.assertTrue(e.getCause() instanceof FileNotFoundException);
		}
		
		String filePath = FileServiceImplTestCase.class.getResource("/data/52-128-255-Happy-UTF-8.HL7")
			.getFile();
			logger.info(String.format("Full path -> %s", filePath));
			file = new File(filePath);
			
		try {
			String data = fileService.read(file, null);
			Assert.assertNotNull(data);
			Assert.assertTrue(data.length()>0);
			logger.info(String.format("Data -> \n %s", data));
		} catch (FileServiceException e) {
			logger.info(String.format("Exception of type %s raised. Error message -> %s.",e.getClass().toString(),e.getMessage()));
			Assert.fail("Exception is not expected!");
		}
		
	}

}
