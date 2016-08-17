package com.ontariomd.hl7.client;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ontariomd.hl7.service.file.FileService;
import com.ontariomd.hl7.service.file.impl.CheckExistsFileServiceImpl;
import com.ontariomd.hl7.service.file.impl.FileServiceImpl;
import com.ontariomd.hl7.service.message.MessageService;
import com.ontariomd.hl7.service.message.bean.MessageInfoBean;
import com.ontariomd.hl7.service.message.impl.HL7v2MessageServiceImpl;

/**
 * Hello world!
 *
 */
public class App {

	private static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) throws Exception{

		logger.info("Starting  simple-hl7-client app...");
		if (args == null || args.length < 1) {
			logger.error("No parameters found. Expected at least one (file path)");
		} else {

			File messageFile = new File(args[0]);
			String encoding = "UTF-8";
			String server = null;
			int port = -1;
			boolean useTls = false;

			logger.info(
					"About to send a message from file ->" + messageFile.getAbsolutePath() + ", encoding->" + encoding);

			FileService fileService = new CheckExistsFileServiceImpl(new FileServiceImpl());
			String data = fileService.read(messageFile, encoding);
			
			MessageInfoBean messageInfoBean = new MessageInfoBean();
			messageInfoBean.setData(data);
			
			MessageService messageService = new HL7v2MessageServiceImpl(server,port,useTls);
			messageService.send(messageInfoBean);
			
			logger.info("Message sent.");
		}

	}
}
