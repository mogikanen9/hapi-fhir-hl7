package com.ontariomd.hl7.service.message.impl;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.ontariomd.hl7.service.file.FileService;
import com.ontariomd.hl7.service.file.FileServiceException;
import com.ontariomd.hl7.service.file.impl.FileServiceImpl;
import com.ontariomd.hl7.service.message.MessageService;
import com.ontariomd.hl7.service.message.MessageServiceException;
import com.ontariomd.hl7.service.message.bean.MessageRequest;
import com.ontariomd.hl7.service.message.bean.MessageResponse;

public class HL7v2MessageServiceImplTestCase {

	private static final Logger logger = LogManager.getLogger(HL7v2MessageServiceImplTestCase.class);
	
	@Test
	public void testHL7v2MessageServiceImpl() {
		HL7v2MessageServiceImpl messageService = new HL7v2MessageServiceImpl(null,-1, false);
		Assert.assertEquals(messageService.getServer(),null);
		Assert.assertTrue(messageService.getPort()==-1);
		Assert.assertEquals(messageService.isUseTls(),false);
	}

	@Test
	public void testSend() throws FileServiceException, MessageServiceException {
		String server = "10.10.11.144";
		int port = 43730;
		MessageService messageService = new HL7v2MessageServiceImpl(server,port, false);
		
		
		//read real data
		String filePath = HL7v2MessageServiceImplTestCase.class.getResource("/data/52-128-255-Happy-UTF-8.HL7")
				.getFile();
				logger.info(String.format("Full path -> %s", filePath));
		FileService fileService = new FileServiceImpl();		
		String data = fileService.read(new File(filePath), "UTF-8");						
		
		MessageRequest msgRequest = new MessageRequest();
		msgRequest.setBody(data);
		msgRequest.setEncoding("UTF-8");
		
	    MessageResponse msgResponse = messageService.send(msgRequest);
		logger.info(String.format("Call to server was successfull. Received response -> \n %s",msgResponse.getBody()));	
		
	}

}
