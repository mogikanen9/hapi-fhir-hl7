package com.ontariomd.hl7.service.file.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ontariomd.hl7.service.file.FileService;
import com.ontariomd.hl7.service.file.FileServiceException;

public class FileServiceImpl implements FileService {

	private static final Logger logger = LogManager.getLogger(FileServiceImpl.class);

	@Override
	public String read(File file, String encoding) throws FileServiceException {
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			return IOUtils.toString(input, encoding);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new FileServiceException(ex.getMessage(), ex);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

}
