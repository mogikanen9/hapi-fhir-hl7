package com.ontariomd.hl7.service.message;

import com.ontariomd.hl7.service.message.bean.MessageRequest;
import com.ontariomd.hl7.service.message.bean.MessageResponse;

public interface MessageService {

	MessageResponse send (MessageRequest messageRequest) throws MessageServiceException;
}
