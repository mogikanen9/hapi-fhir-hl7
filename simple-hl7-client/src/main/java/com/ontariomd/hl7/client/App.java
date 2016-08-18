package com.ontariomd.hl7.client;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ontariomd.hl7.service.file.FileService;
import com.ontariomd.hl7.service.file.impl.CheckExistsFileServiceImpl;
import com.ontariomd.hl7.service.file.impl.FileServiceImpl;
import com.ontariomd.hl7.service.message.MessageService;
import com.ontariomd.hl7.service.message.bean.MessageRequest;
import com.ontariomd.hl7.service.message.bean.MessageResponse;
import com.ontariomd.hl7.service.message.impl.HL7v2MessageServiceImpl;

/**
 * Hello world!
 *
 */
public class App {

	private static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) throws Exception{

		logger.info("Starting  simple-hl7-client app...");
		if (args == null || args.length!=4){
			logger.error(String.format("Four parameters are expected: <full_path_to_message_file> <encoding> <server_ip> <server_port>. Found only %d parameters.",args.length));
		}else{

			File messageFile = new File(args[0]);
			String encoding = args[1];
			String server = args[2];
			int port = Integer.parseInt(args[3]);
			boolean useTls = false;

			logger.info(String.format(
					"About to send a message from \nfile -> %s, \nencoding -> %s, \nserver -> %s, \nport -> %d",messageFile.getAbsolutePath(),encoding,server,port));

			FileService fileService = new CheckExistsFileServiceImpl(new FileServiceImpl());
			String data = fileService.read(messageFile, encoding);
			
			MessageRequest msgRequest = new MessageRequest();
			msgRequest.setBody(data);
			msgRequest.setEncoding(encoding);
			
			MessageService messageService = new HL7v2MessageServiceImpl(server,port,useTls);
			MessageResponse msgResponse = messageService.send(msgRequest);
			
			logger.info(String.format("Call to server was successfull. Received response -> \n %s",msgResponse.getBody()));
		}

	}
}
