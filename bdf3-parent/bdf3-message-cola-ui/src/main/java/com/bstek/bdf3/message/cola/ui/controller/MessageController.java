package com.bstek.bdf3.message.cola.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstek.bdf3.message.cola.ui.service.MessageService;
import com.bstek.bdf3.message.domain.Chat;
import com.bstek.bdf3.message.domain.Notify;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月4日
 */
@Controller
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(path = "/message", method = RequestMethod.GET)
	public String list() {
		return messageService.getListPage();
	}
	
	@RequestMapping(path = "/chat/{chatId}", method = RequestMethod.GET)
	public String list(@PathVariable String chatId, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return messageService.getChatPage(chatId, user.getUsername());
	}
	
	@RequestMapping(path = "/message/send", method = RequestMethod.GET)
	public String send() {
		return messageService.getSendPage();
	}
	
	@RequestMapping(path = "/api/message/load", method = RequestMethod.GET)
	@ResponseBody
	public List<Chat> load(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return messageService.load(user.getUsername());
	}
	
	@RequestMapping(path = "/api/message/load-unread", method = RequestMethod.GET)
	@ResponseBody
	public List<Notify> loadUnread(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return messageService.loadUnread(user.getUsername());
	}
	
	@RequestMapping(path = "/api/message/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody Notify notify, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return messageService.add(notify.getContent(), user.getUsername(), (String) notify.getAdditional());
	}

	
	@RequestMapping(path = "/api/message/remove/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void remove(@PathVariable String id) {
		messageService.remove(id);
	}
	
	@RequestMapping(path = "/api/chat/remove/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeChat(@PathVariable String id) {
		messageService.removeChat(id);
	}
	
	@RequestMapping(path = "/api/chat/clear", method = RequestMethod.DELETE)
	@ResponseBody
	public void clearChat(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		messageService.clearChat(user.getUsername());
	}
	
	@RequestMapping(path = "/api/chat/{chatId}", method = RequestMethod.GET)
	@ResponseBody
	public Chat clearChat(@PathVariable String chatId, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return messageService.getChat(chatId, user);
	}

}
