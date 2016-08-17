package com.ontariomd.hl7.service.message;

import com.ontariomd.hl7.service.message.bean.MessageInfoBean;

public interface MessageService {

	void send (MessageInfoBean message) throws MessageServiceException;
}
