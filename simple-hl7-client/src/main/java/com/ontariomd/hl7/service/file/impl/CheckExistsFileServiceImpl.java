package com.ontariomd.hl7.service.file.impl;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ontariomd.hl7.service.file.FileService;
import com.ontariomd.hl7.service.file.FileServiceException;

public class CheckExistsFileServiceImpl implements FileService{

	private static final Logger logger = LogManager.getLogger(CheckExistsFileServiceImpl.class);
	
	private FileService target;		

	public FileService getTarget() {
		return target;
	}

	public void setTarget(FileService target) {
		this.target = target;
	}

	public CheckExistsFileServiceImpl(FileService target) {
		super();
		this.target = target;
	}
	
	@Override
	public String read(File file, String encoding) throws FileServiceException {
		
		if(file==null){
			throw new FileServiceException ("Parameter 'file' cannot be null!");
		}else if(!file.exists()){
			throw new FileServiceException (String.format("File %s does not exist!", file.getAbsolutePath()));
		}else{
			logger.info(String.format("Reading data from file -> %s",file.getAbsolutePath()));
			return target.read(file, encoding);		
		}
	
	}

}
