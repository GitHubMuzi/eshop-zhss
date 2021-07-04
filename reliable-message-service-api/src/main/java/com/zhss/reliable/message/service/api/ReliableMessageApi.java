package com.zhss.reliable.message.service.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/reliableMessage")  
public interface ReliableMessageApi {
	
	@RequestMapping(value = "/prepare", method = RequestMethod.POST) 
	public Long prepareMessage(@RequestParam("message") String message);
	
	@RequestMapping(value = "/confirm", method = RequestMethod.PUT) 
	public Boolean confirmMessage(@RequestParam("messageId") Long messageId);  
	
	@RequestMapping(value = "/remove", method = RequestMethod.PUT)   
	public Boolean removeMessage(@RequestParam("messageId") Long messageId);
	
	@RequestMapping(value = "/finish", method = RequestMethod.PUT)   
	public Boolean finishMessage(@RequestParam("messageId") Long messageId);
	
}
