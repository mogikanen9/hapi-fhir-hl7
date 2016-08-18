package com.ontariomd.hl7.service.file.impl;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.ontariomd.hl7.service.file.FileService;
import com.ontariomd.hl7.service.file.FileServiceException;

public class CheckExistsFileServiceImplTestCase {

	private static final Logger logger = LogManager.getLogger();

	@Test
	public void testCheckExistsFileServiceImpl() {
		FileService fileService = new CheckExistsFileServiceImpl(null);
		Assert.assertEquals(((CheckExistsFileServiceImpl) fileService).getTarget(), null);
		logger.info("testCheckExistsFileServiceImpl executed");
	}

	@Test
	public void testRead() {

		FileService target = Mockito.mock(FileService.class);		
		FileService fileService = new CheckExistsFileServiceImpl(target);

		File file = null;
		String encoding = null;
		try {
			fileService.read(file, encoding);
		} catch (FileServiceException e) {
			logger.info(String.format("Got exception with message -> %s", e.getMessage()));
			Assert.assertTrue(e.getMessage().startsWith("Parameter 'file' cannot be null!"));
		}

		file = new File("/usr/opt/aaa/xxx/kakaziabla.txt");
		try {
			fileService.read(file, encoding);
		} catch (FileServiceException e) {
			logger.info(String.format("Got exception with message -> %s", e.getMessage()));
			Assert.assertTrue(e.getMessage().contains((" does not exist!")));
		}

		String filePath = CheckExistsFileServiceImplTestCase.class.getResource("/data/52-128-255-Happy-UTF-8.HL7")
				.getFile();
		logger.info(String.format("Full path -> %s", filePath));
		file = new File(filePath);

		try {
			
			Mockito.when(target.read(file, null)).thenReturn("hl7 data is there");
			String data = fileService.read(file, encoding);
			Assert.assertEquals(data,"hl7 data is there");
			logger.info(String.format("mock data returned -> %s", data));
			
		} catch (FileServiceException e) {
			Assert.fail(String.format(
					"Should not be any exception. Unfortunately FileServiceException was raised, message -> %s",
					e.getMessage()));
		}
	}

}
